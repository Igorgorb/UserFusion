package ua.igorg.userfusion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ua.igorg.userfusion.config.DataSourceSupplier;

@SpringBootTest({"spring.cloud.config.enabled=false"})
@DisplayName("UserFusionBackendApplicationTests")
@ComponentScan(basePackages = {"ua.igorg.userfusion"})
@Import({DataSourceSupplier.class})
class UserFusionBackendApplicationTests {

  @Test
  void contextLoads() {}
}
