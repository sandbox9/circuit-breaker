package thor.connector.db.mybatis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"thor.connector.db.mybatis.repository"})
public class ApplicationConfig {

}
