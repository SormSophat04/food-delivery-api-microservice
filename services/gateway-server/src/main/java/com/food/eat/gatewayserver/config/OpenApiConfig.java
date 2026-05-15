package com.food.eat.gatewayserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Delivery API Gateway")
                        .version("1.0.0")
                        .description("Central API Gateway for the Food Delivery microservices platform. Routes requests to the appropriate backend services.")
                        .contact(new Contact().name("Food Delivery API")));
    }
}
