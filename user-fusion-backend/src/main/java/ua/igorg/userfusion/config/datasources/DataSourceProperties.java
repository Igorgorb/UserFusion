package ua.igorg.userfusion.config.datasources;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ua.igorg.userfusion.config.datasources.model.DataSource;

/** Created by igorg on 02.06.2024 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "spring")
public class DataSourceProperties {

  private List<DataSource> dataSources = new ArrayList<>();
}
