package ua.igorg.userfusion.it;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractContainerDatabaseTest {

    static DockerImageName MSSQL_SERVER_IMAGE = DockerImageName.parse("mcr.microsoft.com/mssql/server:2022-CU14-ubuntu-22.04");
    static DockerImageName MYSQL_LATEST_IMAGE = DockerImageName.parse("mysql:latest");
    static DockerImageName POSTGRES_TEST_IMAGE = DockerImageName.parse("postgres:latest");

    protected static final MSSQLServerContainer<?> mssqlServer;
    protected static final MySQLContainer<?> mysql;
    protected static final PostgreSQLContainer<?> postgres;

    static {
        mssqlServer = new MSSQLServerContainer<>(MSSQL_SERVER_IMAGE).acceptLicense();
        mssqlServer.start();

        var msDelegate = new JdbcDatabaseDelegate(mssqlServer, "");
        ScriptUtils.runInitScript(msDelegate, "sql/mssql_create_and_fill_table.sql");

        mysql = new MySQLContainer<>(MYSQL_LATEST_IMAGE);
        mysql.start();

        var myDelegate = new JdbcDatabaseDelegate(mysql, "");
        ScriptUtils.runInitScript(myDelegate, "sql/mysql_create_and_fill_table.sql");

        postgres = new PostgreSQLContainer<>(POSTGRES_TEST_IMAGE);
        postgres.start();

        var pgDelegate = new JdbcDatabaseDelegate(postgres, "");
        ScriptUtils.runInitScript(pgDelegate, "sql/pg1_create_and_fill_table.sql");
        ScriptUtils.runInitScript(pgDelegate, "sql/pg2_create_and_fill_table.sql");
    }

    protected ResultSet performQuery(JdbcDatabaseContainer<?> container, String sql) throws SQLException {
        DataSource ds = getDataSource(container);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }

    protected DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }
}