package com.food.eat.restaurantservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant Service API")
                        .version("1.0.0")
                        .description("Restaurant and promotion management service. Handles restaurant profiles, menus, and promotional offers.")
                        .contact(new Contact().name("Food Delivery API")));
    }
}
