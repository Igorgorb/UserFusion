package ua.igorg.userfusion.it;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.igorg.userfusion.config.OpenApiValidationConfig.OA3_SPEC;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.config.datasources.model.DataSource;
import ua.igorg.userfusion.config.datasources.model.DatabaseStrategy;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIT extends AbstractContainerDatabaseTest {

    public static final String JDBC_URL_STUB = "jdbc:stub:url";

    @Autowired
    WebApplicationContext wac;

    @Autowired
    DataSourceProperties dataSourceProperties;

    protected MockMvc mockMvc;

    final Function<DatabaseStrategy, JdbcDatabaseContainer> containerSelector =
            ds ->
                    switch (ds) {
                        case SQLSERVER -> sqlContainer;
                        case MYSQL -> mysql;
                        case POSTGRES -> postgres;
                        default -> throw new IllegalStateException("Unexpected value: " + ds);
                    };

    final Consumer<DataSource> changer =
            ds -> {
                final var jdbcContainer = containerSelector.apply(ds.getStrategy());
                ds.setUser(jdbcContainer.getUsername());
                ds.setPassword(jdbcContainer.getPassword());
                ds.setUrl(jdbcContainer.getJdbcUrl());
            };

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @AfterEach
    public void tearDown() {
        closeDataSource();
    }

    @Test
    @DisplayName("Get all users from all sources")
    public void testGetUsers() throws Exception {
        // corrected connection urls and credentials for test's environment
        dataSourceProperties.getDataSources().forEach(changer);

        final ResultActions perform = mockMvc.perform(get("/users"));
        checkResultRequest(perform, 80);
    }

    @Test
    @DisplayName("Get all users from all sources without MS Sql Server")
    public void testGetUsersWithoutMSSqlSource() throws Exception {
        // corrected connection urls and credentials for test's environment
        correctDataSourceProperties(DatabaseStrategy.SQLSERVER);

        final ResultActions perform = mockMvc.perform(get("/users"));
        checkResultRequest(perform, 60);
    }

    @Test
    @DisplayName("Get all users from all sources without MySql")
    public void testGetUsersWithoutMySqlSource() throws Exception {
        // corrected connection urls and credentials for test's environment
        correctDataSourceProperties(DatabaseStrategy.MYSQL);

        final ResultActions perform = mockMvc.perform(get("/users"));
        checkResultRequest(perform, 60);
    }

    @Test
    @DisplayName("Get all users from all sources without Postgresql")
    public void testGetUsersWithoutPostgresqlSource() throws Exception {
        // corrected connection urls and credentials for test's environment
        correctDataSourceProperties(DatabaseStrategy.POSTGRES);

        final ResultActions perform = mockMvc.perform(get("/users"));
        checkResultRequest(perform, 40);
    }

    @Test
    @DisplayName("Get nothing users when nothing sources available")
    public void testGetUsersWithoutAllSources() throws Exception {
        dataSourceProperties.getDataSources()
                .forEach(d -> d.setUrl(JDBC_URL_STUB));

        final ResultActions perform = mockMvc.perform(get("/users"));
        checkResultRequest(perform, 0);
    }

    @DisplayName("Get filtered users from all sources")
    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("ua.igorg.userfusion.it.AbstractContainerDatabaseTest#getFiltersForTest")
    public void testGetFilteredUsers(final String filter, final Integer expectCount) throws Exception {
        // corrected connection urls and credentials for test's environment
        dataSourceProperties.getDataSources().forEach(changer);

        final ResultActions perform = mockMvc.perform(get("/users" + filter));
        checkResultRequest(perform, expectCount);
    }

    private void checkResultRequest(final ResultActions perform, final int expected) throws Exception {
        perform
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(expected)))
                .andExpect(openApi().isValid(OA3_SPEC));
    }

    private void correctDataSourceProperties(final DatabaseStrategy strategy) {
        dataSourceProperties.getDataSources().stream()
                .peek(d -> d.setUrl(JDBC_URL_STUB))
                .filter(d -> !d.getStrategy().equals(strategy))
                .forEach(changer);
    }
}
