package com.ess.essreddit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openapiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("EssReddit API")
                        .description("The springboot REST API for the backend of my clone of Reddit")
                        .version("1.0.0")
                );
    }
}
