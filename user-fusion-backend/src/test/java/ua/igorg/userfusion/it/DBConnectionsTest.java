package ua.igorg.userfusion.it;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@Nested
class DBConnectionsTest extends AbstractContainerDatabaseTest {

  @AfterEach
  public void tearDown() {
    closeDataSource();
  }

  @Test
  @DisplayName("Check MS SQLServer Container")
  public void testSimpleMsSqlServer() throws SQLException {
    final int resultSetInt = performQuery(sqlContainer, "SELECT 1");
    assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
    assertHasCorrectExposedAndLivenessCheckPorts(sqlContainer, MSSQLServerContainer.MS_SQL_SERVER_PORT);
  }

  @Test
  @DisplayName("Check MySql Container")
  public void testSimpleMySql() throws SQLException {
    final int resultSetInt = performQuery(mysql, "SELECT 1");
    assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
    assertHasCorrectExposedAndLivenessCheckPorts(mysql, MySQLContainer.MYSQL_PORT);
  }

  @Test
  @DisplayName("Check PostgreSql Container")
  public void testSimplePostgreSql() throws SQLException {
    final int resultSetInt = performQuery(postgres, "SELECT 1");
    assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
    assertHasCorrectExposedAndLivenessCheckPorts(postgres, PostgreSQLContainer.POSTGRESQL_PORT);
  }

  private <T extends JdbcDatabaseContainer> void assertHasCorrectExposedAndLivenessCheckPorts(
      final T container, final Integer port) {
    assertThat(container.getExposedPorts())
        .containsExactly(port);
    assertThat(container.getLivenessCheckPortNumbers())
        .containsExactly(container.getMappedPort(port));
  }
}
