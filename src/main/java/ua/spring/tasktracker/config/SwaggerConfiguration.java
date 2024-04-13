package ua.spring.tasktracker.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Value("${app.version}")
    private String version;
    @Value("${api.dev-url}")
    private String devServer;
    @Value("${api.prod-url}")
    private String prodServer;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring First Practice")
                        .version(version)
                        .description("First spring application. Controller with all 5 http methods")
                        .contact(new Contact()
                                .email("d.shlapak2314@gmail.com")
                                .name("Danylo Shlapak")))
                .servers(List.of(new Server().url(devServer), new Server().url(prodServer)));
    }
}
