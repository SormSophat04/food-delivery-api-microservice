package com.food.eat.gatewayserver.service.impl;

import com.food.eat.gatewayserver.entity.ApiRoute;
import com.food.eat.gatewayserver.repository.ApiRouteRepository;
import com.food.eat.gatewayserver.service.GatewayRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteLocatorImpl implements RouteLocator {

    private final ApiRouteRepository apiRouteRepository;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        return apiRouteRepository.findAll().map(apiRoute -> builder.route(apiRoute.getId().toString(),
                predicateSpec -> setPredicateSpec(predicateSpec, apiRoute)))
                .collectList()
                .flatMapMany(builders -> builder.build().getRoutes());
    }

    @Override
    public Flux<Route> getRoutesByMetadata(Map<String, Object> metadata) {
        return RouteLocator.super.getRoutesByMetadata(metadata);
    }

    private Buildable setPredicateSpec(PredicateSpec predicateSpec, ApiRoute apiRoute) {

        BooleanSpec booleanSpec = predicateSpec.path(apiRoute.getPath());
        if (!apiRoute.getMethod().isBlank()){
            booleanSpec.and().method(apiRoute.getMethod());
        }
        if (!apiRoute.getUri().isBlank()){
            booleanSpec.and().uri(apiRoute.getUri());
        }
        return booleanSpec.uri(apiRoute.getUri());
    }
}
