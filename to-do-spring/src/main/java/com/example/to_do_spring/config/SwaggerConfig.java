package com.example.to_do_spring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("To-Do List API")
                        .description("API REST para gestão de tarefas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("João neto")
                                .email("joaonetoads25@gmail.com")
                                .url("https://github.com/JoaoAds2210")))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                                .components(new Components().addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
