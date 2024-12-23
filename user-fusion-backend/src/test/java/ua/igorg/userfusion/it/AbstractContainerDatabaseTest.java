package ua.igorg.userfusion.it;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.provider.Arguments;
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

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
public abstract class AbstractContainerDatabaseTest {

    static final DockerImageName MSSQL_SERVER_IMAGE =
            DockerImageName.parse("mcr.microsoft.com/mssql/server:2022-CU14-ubuntu-22.04");
    static final DockerImageName MYSQL_LATEST_IMAGE = DockerImageName.parse("mysql:latest");
    static final DockerImageName POSTGRES_TEST_IMAGE = DockerImageName.parse("postgres:latest");

    protected static final MSSQLServerContainer<?> sqlContainer;
    protected static final MySQLContainer<?> mysql;
    protected static final PostgreSQLContainer<?> postgres;

    static final JdbcDatabaseDelegate pgDelegate;

    static {
        sqlContainer = new MSSQLServerContainer<>(MSSQL_SERVER_IMAGE).acceptLicense();
        sqlContainer.start();

        final var msDelegate = new JdbcDatabaseDelegate(sqlContainer, "");
        ScriptUtils.runInitScript(msDelegate, "sql/mssql_create_and_fill_table.sql");

        mysql = new MySQLContainer<>(MYSQL_LATEST_IMAGE);
        mysql.start();

        final var myDelegate = new JdbcDatabaseDelegate(mysql, "");
        ScriptUtils.runInitScript(myDelegate, "sql/mysql_create_and_fill_table.sql");

        postgres = new PostgreSQLContainer<>(POSTGRES_TEST_IMAGE);
//        postgres = getPostgreSQLContainer();
        postgres.start();

        pgDelegate = new JdbcDatabaseDelegate(postgres, "");
        ScriptUtils.runInitScript(pgDelegate, "sql/pg1_create_and_fill_table.sql");
        ScriptUtils.runInitScript(pgDelegate, "sql/pg2_create_and_fill_table.sql");
    }

    private final Map<JdbcDatabaseContainer, DataSource> dataSources = new HashMap<>();

    protected int performQuery(final JdbcDatabaseContainer<?> container, final String sql)
            throws SQLException {
        final DataSource dataSource = getDataSource(container);
        final Statement statement = dataSource.getConnection().createStatement();
        statement.execute(sql);
        final ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        final int resultSetInt = resultSet.getInt(1);

        resultSet.close();
        statement.close();

        return resultSetInt;
    }

    protected DataSource getDataSource(final JdbcDatabaseContainer<?> container) {
        return dataSources.computeIfAbsent(container,
                k -> {
                    final HikariConfig hikariConfig = new HikariConfig();
                    hikariConfig.setMaximumPoolSize(1);
                    hikariConfig.setMinimumIdle(2);
                    hikariConfig.setJdbcUrl(container.getJdbcUrl());
                    hikariConfig.setUsername(container.getUsername());
                    hikariConfig.setPassword(container.getPassword());
                    hikariConfig.setDriverClassName(container.getDriverClassName());
                    return new HikariDataSource(hikariConfig);
                });
    }

    protected void closeDataSource() {
        log.debug("Closing dataSources");
        dataSources.forEach((k, v) -> ((HikariDataSource) v).close());
        dataSources.clear();
    }

    /**
     * Create custom container based on PostgreSQLContainer.
     * when increase max_connections
     *
     * @return PostgreSQLContainer
     */
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

    public static Stream<Arguments> getFiltersForTest() {
        return Stream.of(
                Arguments.of("?username=vivamus.nisi@icloud.couk", 1),
                Arguments.of("?name=Blaze", 2),
                Arguments.of("?surname=Schmidt", 2),

                Arguments.of("?username=vivamus.nisi@icloud.couk&name=Ina", 1),
                Arguments.of("?name=Ina&username=vivamus.nisi@icloud.couk", 1),

                Arguments.of("?username=vivamus.nisi@icloud.couk&surname=Richard", 1),
                Arguments.of("?name=Ina&surname=Richard", 1),

                Arguments.of("?username=vivamus.nisi@icloud.couk&name=Ina&surname=Richard", 1),
                Arguments.of("?username=vivamus.nisi@icloud.couk&surname=Richard&name=Ina", 1),

                Arguments.of("?name=Ina&username=vivamus.nisi@icloud.couk&surname=Richard", 1),
                Arguments.of("?name=Ina&surname=Richard&username=vivamus.nisi@icloud.couk", 1),

                Arguments.of("?surname=Richard&username=vivamus.nisi@icloud.couk&name=Ina", 1),
                Arguments.of("?surname=Richard&name=Ina&username=vivamus.nisi@icloud.couk", 1)
        );
    }

    @PreDestroy
    public void destroy() {
        if (sqlContainer.isRunning()) {
            sqlContainer.stop();
        }
        if (mysql.isRunning()) {
            mysql.stop();
        }
        if (postgres.isRunning()) {
            postgres.stop();
        }
    }
}
