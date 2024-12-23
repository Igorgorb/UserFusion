package ua.igorg.userfusion.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by igorg on 01.06.2024
 */
@Slf4j
@Configuration
public class DataSourceSupplier {

    private final Map<String, DataSource> dataSources = new HashMap<>();
    private final Map<String, JdbcTemplate> jdbcTemplates = new HashMap<>();

    @PreDestroy
    public void shutdown() {
        jdbcTemplates.clear();
        dataSources.forEach((k, v) -> ((HikariDataSource) v).close());
        dataSources.clear();
    }

    @Bean
    public DataSource getDataSource() {
        return getDataSource("org.h2.Driver", "jdbc:h2:mem:test", "sa", "");
    }

    public DataSource getDataSource(
            final String driverClassName,
            final String jdbcUrl,
            final String username,
            final String password) {
        log.debug(
                "They suggested creating a connection using the following information: DB Driver: [{}] URL: [{}], Login: [{}], Pass: [{}]",
                driverClassName,
                jdbcUrl,
                username,
                password);

        return dataSources.computeIfAbsent(
                getDataSourceInfo(driverClassName, jdbcUrl, username, password),
                k -> {
                    final Class<?> driverClass =
                            ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
                    return DataSourceBuilder.create()
                            .driverClassName(driverClassName)
                            .url(jdbcUrl)
                            .username(username)
                            .password(password)
                            .build();
                });
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
        return jdbcTemplates.computeIfAbsent(
                getDataSourceInfo(driverClassName, jdbcUrl, username, password),
                k -> new JdbcTemplate(getDataSource(driverClassName, jdbcUrl, username, password))
        );
    }

    private String getDataSourceInfo(final String driverClassName, final String jdbcUrl, final String username, final String password) {
        return "%s%s%s%s".formatted(
                driverClassName,
                username,
                password,
                jdbcUrl
        );
    }
}
