package ua.igorg.userfusion.it;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.igorg.userfusion.config.OpenApiValidationConfig.OA3_SPEC;

import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.config.datasources.model.DataSource;
import ua.igorg.userfusion.config.datasources.model.DatabaseStrategy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIT extends AbstractContainerDatabaseTest {

  @Autowired
  WebApplicationContext wac;

  @Autowired
  DataSourceProperties dataSourceProperties;

  protected MockMvc mockMvc;

  Function<DatabaseStrategy, JdbcDatabaseContainer> containerSelector =
      ds ->
          switch (ds) {
            case SQLSERVER -> mssqlServer;
            case MYSQL -> mysql;
            case POSTGRES -> postgres;
            default -> throw new IllegalStateException("Unexpected value: " + ds);
          };

  Consumer<DataSource> changer =
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

  @DirtiesContext
  @Test
  @DisplayName("Get all users from all sources")
  public void testGetUsers() throws Exception {
    // corrected connection urls and credentials for test's environment
    dataSourceProperties.getDataSources().forEach(changer);

    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(80)))
        .andExpect(openApi().isValid(OA3_SPEC));
  }

  @DirtiesContext
  @Test
  @DisplayName("Get all users from all sources without MS Sql Server")
  public void testGetUsersWithoutMSSqlSource() throws Exception {
    // corrected connection urls and credentials for test's environment
    dataSourceProperties.getDataSources().stream()
        .filter(d -> !d.getStrategy().equals(DatabaseStrategy.SQLSERVER))
        .forEach(changer);

    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(60)))
        .andExpect(openApi().isValid(OA3_SPEC));
  }

  @DirtiesContext
  @Test
  @DisplayName("Get all users from all sources without MySql")
  public void testGetUsersWithoutMySqlSource() throws Exception {
    // corrected connection urls and credentials for test's environment
    dataSourceProperties.getDataSources().stream()
        .filter(d -> !d.getStrategy().equals(DatabaseStrategy.MYSQL))
        .forEach(changer);

    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(60)))
        .andExpect(openApi().isValid(OA3_SPEC));
  }

  @DirtiesContext
  @Test
  @DisplayName("Get all users from all sources without Postgresql")
  public void testGetUsersWithoutPostgresqlSource() throws Exception {
    // corrected connection urls and credentials for test's environment
    dataSourceProperties.getDataSources().stream()
        .filter(d -> !d.getStrategy().equals(DatabaseStrategy.POSTGRES))
        .forEach(changer);

    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(40)))
        .andExpect(openApi().isValid(OA3_SPEC));
  }

  @DirtiesContext
  @Test
  @DisplayName("Get nothing users when nothing sources available")
  public void testGetUsersWithoutAllSources() throws Exception {
    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(0)))
        .andExpect(openApi().isValid(OA3_SPEC));
  }
}
