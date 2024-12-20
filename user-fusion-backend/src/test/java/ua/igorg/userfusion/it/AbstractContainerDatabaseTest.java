package ua.igorg.userfusion.it;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import javax.sql.DataSource;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.PathResource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.utility.DockerImageName;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
public abstract class AbstractContainerDatabaseTest {

    static final DockerImageName MSSQL_SERVER_IMAGE =
            DockerImageName.parse("mcr.microsoft.com/mssql/server:2022-CU14-ubuntu-22.04");
    static final DockerImageName MYSQL_LATEST_IMAGE = DockerImageName.parse("mysql:latest");
//    static final DockerImageName POSTGRES_TEST_IMAGE = DockerImageName.parse("postgres:latest");

    protected static final MSSQLServerContainer<?> mssqlServer;
    protected static final MySQLContainer<?> mysql;
    protected static final PostgreSQLContainer<?> postgres;

    static {
        mssqlServer = new MSSQLServerContainer<>(MSSQL_SERVER_IMAGE).acceptLicense();
        mssqlServer.start();

        final var msDelegate = new JdbcDatabaseDelegate(mssqlServer, "");
        ScriptUtils.runInitScript(msDelegate, "sql/mssql_create_and_fill_table.sql");

        mysql = new MySQLContainer<>(MYSQL_LATEST_IMAGE);
        mysql.start();

        final var myDelegate = new JdbcDatabaseDelegate(mysql, "");
        ScriptUtils.runInitScript(myDelegate, "sql/mysql_create_and_fill_table.sql");

//        postgres = new PostgreSQLContainer<>(POSTGRES_TEST_IMAGE);
        postgres = getPostgreSQLContainer();
        postgres.start();

        final var pgDelegate = new JdbcDatabaseDelegate(postgres, "");
        ScriptUtils.runInitScript(pgDelegate, "sql/pg1_create_and_fill_table.sql");
        ScriptUtils.runInitScript(pgDelegate, "sql/pg2_create_and_fill_table.sql");
    }

    protected ResultSet performQuery(final JdbcDatabaseContainer<?> container, final String sql)
            throws SQLException {
        final DataSource ds = getDataSource(container);
        final Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        final ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }

    protected DataSource getDataSource(final JdbcDatabaseContainer<?> container) {
        final HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setMaximumPoolSize(2);
//        hikariConfig.setMinimumIdle(2);
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }

    @SneakyThrows
    static PostgreSQLContainer<?> getPostgreSQLContainer() {
        final PathResource path = new PathResource("src/test/resources/docker/postgresql/Dockerfile");
        log.info("path: {}", path);
        final ImageFromDockerfile image = new ImageFromDockerfile()
                .withDockerfile(path.getFile().toPath());
        final DockerImageName imageName = DockerImageName.parse(image.get())
                .asCompatibleSubstituteFor(PostgreSQLContainer.IMAGE);
        return new PostgreSQLContainer<>(imageName)
                .waitingFor(new LogMessageWaitStrategy()
                        .withRegEx(".*database system is ready to accept connections.*\\s")
                        .withTimes(2)
                        .withStartupTimeout(Duration.of(60, SECONDS)));
    }
}
