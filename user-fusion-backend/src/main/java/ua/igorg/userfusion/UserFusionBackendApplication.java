package ua.igorg.userfusion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import ua.igorg.userfusion.config.DataSourceSupplier;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"ua.igorg.userfusion", "ua.userfusion"})
@Import({DataSourceSupplier.class})
public class UserFusionBackendApplication implements CommandLineRunner {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    public static void main(final String[] args) {
        SpringApplication.run(UserFusionBackendApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("CommandLineRunner start run");
        System.out.println(dataSourceProperties.getDataSources());
        System.out.println("CommandLineRunner end run");
    }
}
