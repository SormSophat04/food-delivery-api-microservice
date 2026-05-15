package com.food.eat.deliveryservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI deliveryServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery Service API")
                        .version("1.0.0")
                        .description("Delivery and address management service. Handles user addresses, delivery drivers, and delivery tracking.")
                        .contact(new Contact().name("Food Delivery API")));
    }
}
