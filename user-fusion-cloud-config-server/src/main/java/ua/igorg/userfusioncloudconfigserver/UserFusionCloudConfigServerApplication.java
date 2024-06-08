package ua.igorg.userfusioncloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class UserFusionCloudConfigServerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(UserFusionCloudConfigServerApplication.class, args);
	}
}
