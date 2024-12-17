package ua.igorg.userfusion.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.igorg.userfusion.config.DataSourceSupplier;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.core.domain.User;
import ua.igorg.userfusion.core.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author igorg
 * @create 01.06.2024
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final DataSourceSupplier dataSourceSupplier;

    private final DataSourceProperties dataSourceProperties;

    public UserServiceImpl(final DataSourceSupplier dataSourceSupplier,
                           final DataSourceProperties dataSourceProperties) {
        this.dataSourceSupplier = dataSourceSupplier;
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    public List<User> getUsers() {
        final List<User> list = new ArrayList<>();
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
                LOG.error("Strategy: %s, Url: [%s]".formatted(
                        d.getStrategy(),
                        d.getUrl()
                ), ex);
            }
            if (Objects.nonNull(results)) {
                list.addAll(results);
            }
        });
        LOG.info("The result contains {} rows", list.size());
        return Collections.unmodifiableList(list);
    }
}
