package com.food.eat.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator localRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        p -> p.path("/food-delivery/auth/**")
                                .filters(f -> f
                                        .rewritePath("/food-delivery/auth/(?<segment>.*)", "/api/auth/${segment}")
                                        .addResponseHeader("Auth-Response-Time", LocalDateTime.now().toString()))
                                .uri("lb://AUTH-SERVICE")
                ).route(
                        p -> p.path("/food-delivery/food/**")
                                .filters(f -> f
                                        .rewritePath("/food-delivery/food/(?<segment>.*)", "/${segment}"))
                                .uri("lb://FOOD-SERVICE")
                ).route(
                        p -> p.path("/food-delivery/delivery/**")
                                .filters(f -> f
                                        .rewritePath("/food-delivery/delivery/(?<segment>.*)", "/${segment}"))
                                .uri("lb://DELIVERY-SERVICE")
                ).route(
                        p -> p.path("/food-delivery/order/**")
                                .filters(f -> f
                                        .rewritePath("/food-delivery/order/(?<segment>.*)", "/${segment}"))
                                .uri("lb://ORDER-SERVICE")
                ).route(
                        p -> p.path("/food-delivery/restaurant/**")
                                .filters(f -> f
                                        .rewritePath("/food-delivery/restaurant/(?<segment>.*)", "/${segment}"))
                                .uri("lb://RESTAURANT-SERVICE")
                ).build();
    }
}
