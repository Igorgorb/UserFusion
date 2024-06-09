package ua.igorg.userfusion.config.datasources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

/**
 * @Author igorg
 * @create 01.06.2024
 */
@Configuration
public class DataSourceFactoryImpl implements DataSourceFactory {

	private static final Logger LOG = LoggerFactory.getLogger(DataSourceFactoryImpl.class);

	@Bean
	@Override
	public DataSource getDataSource() {
		final String driverClassName = "org.h2.Driver";
		final String jdbcUrl = "jdbc:h2:mem:test";
		final String username = "sa";
		final String password = "";
		final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
		return DataSourceBuilder
			.create()
			.driverClassName(driverClassName)
			.url(jdbcUrl)
			.username(username)
			.password(password)
			.build();
	}

	@Override
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
	@Override
	public JdbcTemplate getJdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Override
	public JdbcTemplate getCustomJdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
