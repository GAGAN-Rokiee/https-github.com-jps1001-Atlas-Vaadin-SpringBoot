package it.besolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BeplatformDesignerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeplatformDesignerApplication.class, args);
    }
}
