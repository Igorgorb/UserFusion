package ua.igorg.userfusion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {"ua.igorg.userfusion", "ua.userfusion"})
public class UserFusionBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserFusionBackendApplication.class, args);
    }
}
