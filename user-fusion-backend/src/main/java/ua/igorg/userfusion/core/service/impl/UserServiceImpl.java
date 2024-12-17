package ua.igorg.userfusion.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.igorg.userfusion.config.DataSourceSupplier;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.core.domain.User;
import ua.igorg.userfusion.core.service.UserService;
import ua.igorg.userfusion.mappers.UserMapper;
import ua.userfusion.server.model.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author igorg
 * @create 01.06.2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final DataSourceSupplier dataSourceSupplier;

    private final DataSourceProperties dataSourceProperties;

    @Override
    public List<UserDto> getUsers() {
        final List<UserDto> list = new ArrayList<>();
        dataSourceProperties.getDataSources().forEach(d -> {
            final var jdbcTemplate = dataSourceSupplier.getJdbcTemplate(
                    d.getStrategy().getDriver(),
                    d.getUrl(),
                    d.getUser(),
                    d.getPassword());
            List<User> results = null;
            try {
                results = jdbcTemplate.query(
                        "select t.%s, t.%s, t.%s, t.%s from %s t"
                                .formatted(
                                        d.getMapping().getId(),
                                        d.getMapping().getUsername(),
                                        d.getMapping().getName(),
                                        d.getMapping().getSurname(),
                                        d.getTable()
                                ),
                        (rs, rowNum) -> new User(
                                rs.getString(d.getMapping().getId()),
                                rs.getString(d.getMapping().getUsername()),
                                rs.getString(d.getMapping().getName()),
                                rs.getString(d.getMapping().getSurname())
                        )
                );
            } catch (final Exception ex) {
                log.error("Strategy: %s, Url: [%s]".formatted(
                        d.getStrategy(),
                        d.getUrl()
                ), ex);
            }
            if (Objects.nonNull(results)) {
                list.addAll(userMapper.toDto(results));
            }
        });
        log.info("The result contains {} rows", list.size());
        return Collections.unmodifiableList(list);
    }
}
