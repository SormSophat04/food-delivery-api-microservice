package com.food.eat.gatewayserver.repository;

import com.food.eat.gatewayserver.entity.ApiRoute;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ApiRouteRepository extends R2dbcRepository<ApiRoute, Long> {
    Mono<ApiRoute> findFirstById(Long routeId);
//    Mono<Void> deleteById(@NonNull Long routeId);
}
