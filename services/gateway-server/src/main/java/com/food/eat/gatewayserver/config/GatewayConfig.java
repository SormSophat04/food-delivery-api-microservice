package com.food.eat.gatewayserver.config;

import com.food.eat.gatewayserver.repository.ApiRouteRepository;
import com.food.eat.gatewayserver.service.impl.RouteLocatorDetail;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(ApiRouteRepository apiRouteRepository, RouteLocatorBuilder builder) {
        return new RouteLocatorDetail(apiRouteRepository, builder);
    }
}
