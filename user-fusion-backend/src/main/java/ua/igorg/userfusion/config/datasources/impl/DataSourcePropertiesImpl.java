package ua.igorg.userfusion.config.datasources.impl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;
import ua.igorg.userfusion.config.datasources.model.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author igorg
 * @create 01.06.2024
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "spring")
public class DataSourcePropertiesImpl implements DataSourceProperties {

    private List<DataSource> dataSources = new ArrayList<>();
}
