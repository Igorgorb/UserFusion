package ua.igorg.userfusion.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

/**
 * Created by igorg
 * on 01.06.2024
 */
@Configuration
public class DataSourceSupplier {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceSupplier.class);

    @Bean
    public DataSource getDataSource() {
        return getDataSource("org.h2.Driver",
                "jdbc:h2:mem:test",
                "sa",
                "");
    }

    public DataSource getDataSource(
            final String driverClassName,
            final String jdbcUrl,
            final String username,
            final String password
    ) {
        LOG.debug(
                "They suggested creating a connection using the following information: DB Driver: [{}] URL: [{}], Login: [{}], Pass: [{}]",
                driverClassName,
                jdbcUrl,
                username,
                password
        );
        final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
        return DataSourceBuilder
                .create()
                .driverClassName(driverClassName)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate(
            final String driverClassName,
            final String jdbcUrl,
            final String username,
            final String password) {
        return new JdbcTemplate(getDataSource(driverClassName, jdbcUrl, username, password));
    }
}
