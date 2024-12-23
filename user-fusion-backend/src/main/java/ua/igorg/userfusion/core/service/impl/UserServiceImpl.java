package ua.igorg.userfusion.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.igorg.userfusion.config.DataSourceSupplier;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.core.domain.User;
import ua.igorg.userfusion.core.service.UserService;
import ua.igorg.userfusion.mappers.UserMapper;
import ua.igorg.userfusion.util.FieldName;
import ua.igorg.userfusion.util.SqlBuilder;
import ua.userfusion.server.model.UserDto;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;
import java.util.Optional;
import java.util.Map;
import java.util.stream.IntStream;

import static java.sql.Types.VARCHAR;

/**
 * Created by igorg on 01.06.2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final DataSourceSupplier dataSourceSupplier;

    private final DataSourceProperties dataSourceProperties;

    private static final String sqlQuery =
            "select t.%s, t.%s, t.%s, t.%s from %s t where 1=1 ";

    @Override
    public List<UserDto> getUsers(final Optional<String> username, final Optional<String> name, final Optional<String> surname) {

        final Map<FieldName, Optional<String>> inputParams =
                Map.of(FieldName.USER_NAME, username,
                        FieldName.NAME, name,
                        FieldName.SURNAME, surname);

        final List<UserDto> list = new ArrayList<>();
        dataSourceProperties
                .getDataSources()
                .forEach(
                        d -> {
                            final var jdbcTemplate =
                                    dataSourceSupplier.getJdbcTemplate(
                                            d.getStrategy().getDriver(), d.getUrl(), d.getUser(), d.getPassword());
                            List<User> results = null;
                            final List<String> paramList = new ArrayList<>();
                            try {
                                final SqlBuilder builder = new SqlBuilder(sqlQuery, d, inputParams, paramList);
                                final String sqlQuery = builder.build();
                                results = paramList.isEmpty() ?
                                        jdbcTemplate.query(sqlQuery,
                                                (rs, rowNum) ->
                                                        new User(
                                                                rs.getString(d.getMapping().getId()),
                                                                rs.getString(d.getMapping().getUsername()),
                                                                rs.getString(d.getMapping().getName()),
                                                                rs.getString(d.getMapping().getSurname()))
                                        ) :
                                        jdbcTemplate.query(sqlQuery,
                                                preparedStatement -> IntStream.range(0, paramList.size())
                                                        .forEach(idx -> {
                                                            try {
                                                                log.debug("PreparedStatementSetter: param: [{}]", paramList.get(idx));
                                                                preparedStatement.setObject(idx+1, paramList.get(idx), VARCHAR);
                                                            } catch (final SQLException e) {
                                                                throw new RuntimeException(e);
                                                            }
                                                        }),
                                                (rs, rowNum) ->
                                                        new User(
                                                                rs.getString(d.getMapping().getId()),
                                                                rs.getString(d.getMapping().getUsername()),
                                                                rs.getString(d.getMapping().getName()),
                                                                rs.getString(d.getMapping().getSurname()))
                                        );
                            } catch (final Exception ex) {
                                log.error("Strategy: %s, Url: [%s]".formatted(d.getStrategy(), d.getUrl()), ex);
                            }
                            if (Objects.nonNull(results)) {
                                list.addAll(userMapper.toDto(results));
                            }
                        });
        log.info("The result getUsers with Parameters contains {} rows", list.size());
        return Collections.unmodifiableList(list);
    }
}
