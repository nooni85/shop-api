package com.inca.tachyon.shop.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDocsConfig {
    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact()
                .name("Sangwoo Joo")
                .email("nooni85@inca.co.kr");

        License license = new License().name("Copyright(C) INCA Internet All rights reserved.");

        Info info = new Info()
                .title("API Documentation")
                .version("1.0.0")
                .description("API Description")
                .termsOfService("http://swagger.io/terms/")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
