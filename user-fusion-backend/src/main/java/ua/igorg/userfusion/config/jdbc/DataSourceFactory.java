package ua.igorg.userfusion.config.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author igorg
 * @create 01.06.2024
 */
public interface DataSourceFactory {

	DataSource getDataSource();

	DataSource getDataSource(
		String driverClassName,
		String jdbcUrl,
		String username,
		String password
	);

	JdbcTemplate getJdbcTemplate(DataSource dataSource);

	JdbcTemplate getCustomJdbcTemplate(DataSource dataSource);
}