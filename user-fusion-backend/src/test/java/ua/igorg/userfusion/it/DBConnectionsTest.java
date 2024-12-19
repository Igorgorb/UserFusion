package ua.igorg.userfusion.it;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Nested
class DBConnectionsTest extends AbstractContainerDatabaseTest {

    @Test
    public void testSimpleMsSqlServer() throws SQLException {
        final ResultSet resultSet = performQuery(mssqlServer, "SELECT 1");

        final int resultSetInt = resultSet.getInt(1);
        assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
        assertHasCorrectExposedAndLivenessCheckPorts(mssqlServer);
    }

    @Test
    public void testSimpleMySql() throws SQLException {
        final ResultSet resultSet = performQuery(mysql, "SELECT 1");
        final int resultSetInt = resultSet.getInt(1);

        assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
        assertHasCorrectExposedAndLivenessCheckPorts(mysql);
    }

    @Test
    public void testSimplePostgreSql() throws SQLException {
        final ResultSet resultSet = performQuery(postgres, "SELECT 1");
        final int resultSetInt = resultSet.getInt(1);
        assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
        assertHasCorrectExposedAndLivenessCheckPorts(postgres);
    }

    private void assertHasCorrectExposedAndLivenessCheckPorts(final MSSQLServerContainer<?> mssqlServer) {
        assertThat(mssqlServer.getExposedPorts()).containsExactly(MSSQLServerContainer.MS_SQL_SERVER_PORT);
        assertThat(mssqlServer.getLivenessCheckPortNumbers())
                .containsExactly(mssqlServer.getMappedPort(MSSQLServerContainer.MS_SQL_SERVER_PORT));
    }

    private void assertHasCorrectExposedAndLivenessCheckPorts(final MySQLContainer<?> mysql) {
        assertThat(mysql.getExposedPorts()).containsExactly(MySQLContainer.MYSQL_PORT);
        assertThat(mysql.getLivenessCheckPortNumbers()).containsExactly(mysql.getMappedPort(MySQLContainer.MYSQL_PORT));
    }

    private void assertHasCorrectExposedAndLivenessCheckPorts(final PostgreSQLContainer<?> postgres) {
        assertThat(postgres.getExposedPorts()).containsExactly(PostgreSQLContainer.POSTGRESQL_PORT);
        assertThat(postgres.getLivenessCheckPortNumbers())
                .containsExactly(postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT));
    }
}
