package ua.igorg.userfusion.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.spy;

import javax.sql.DataSource;
import org.h2.Driver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

/** Created by igorg on 07.06.2024 */
class DataSourceSupplierTest {

  final String driver = "org.h2.Driver";
  final String url = "jdbc:h2:mem:testdb";
  final String username = "sa";
  final String password = "";
  private final DataSourceSupplier dataSourceSupplier = spy(new DataSourceSupplier());

  @Test
  void testGetDataSourceDefault() {
    final DataSource dataSource = dataSourceSupplier.getDataSource();
    assertNotNull(dataSource, "The default DataSource should not be null.");
  }

  @Test
  void testGetDataSourceWithParameters() {

    final DataSource dataSource = dataSourceSupplier.getDataSource(driver, url, username, password);

    assertNotNull(dataSource, "The DataSource should not be null.");
    assertDoesNotThrow(
        () -> {
          final Class<?> driverClass = Class.forName(driver);
          assertEquals(Driver.class, driverClass, "The driver class should match.");
        });
  }

  @Test
  void testGetJdbcTemplateDefault() {
    final DataSource mockDataSource = Mockito.mock(DataSource.class);

    final JdbcTemplate jdbcTemplate = dataSourceSupplier.getJdbcTemplate(mockDataSource);

    assertNotNull(jdbcTemplate, "The JdbcTemplate should not be null.");
    assertSame(
        mockDataSource,
        jdbcTemplate.getDataSource(),
        "The DataSource should match the one provided.");
  }

  @Test
  void testGetJdbcTemplateWithParameters() {
    final JdbcTemplate jdbcTemplate =
        dataSourceSupplier.getJdbcTemplate(driver, url, username, password);

    assertNotNull(jdbcTemplate, "The JdbcTemplate should not be null.");
    assertNotNull(
        jdbcTemplate.getDataSource(), "The JdbcTemplate's DataSource should not be null.");
  }
}
