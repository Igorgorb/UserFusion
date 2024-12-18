package ua.igorg.userfusion.it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.config.datasources.model.DataSource;
import ua.igorg.userfusion.config.datasources.model.DatabaseStrategy;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIT extends AbstractContainerDatabaseTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    DataSourceProperties dataSourceProperties;

    protected MockMvc mockMvc;

    Function<DatabaseStrategy, JdbcDatabaseContainer> containerSelector = ds -> switch (ds) {
        case SQLSERVER -> mssqlServer;
        case MYSQL -> mysql;
        case POSTGRES -> postgres;
        default -> throw new IllegalStateException("Unexpected value: " + ds);
    };

    Consumer<DataSource> changer = ds -> {
        var jdbcContainer = containerSelector.apply(ds.getStrategy());
        ds.setUser(jdbcContainer.getUsername());
        ds.setPassword(jdbcContainer.getPassword());
        ds.setUrl(jdbcContainer.getJdbcUrl());
    };

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();

        // corrected connections url and credentials for test env
        dataSourceProperties.getDataSources().forEach(changer);
    }

    @Test
    public void testGetUsers() throws Exception {


        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
}

