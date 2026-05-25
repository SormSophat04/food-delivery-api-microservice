package com.food.eat.gatewayserver.service;

import com.food.eat.gatewayserver.dto.RouteApiRequest;
import com.food.eat.gatewayserver.dto.RouteApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RouteService {
    Mono<RouteApiResponse> create(RouteApiRequest request);
    Mono<RouteApiResponse> update(RouteApiRequest request, Long id);
    Mono<RouteApiResponse> findById(Long id);
    Flux<RouteApiResponse> findAll();
    Mono<Void> deleteById(Long id);
    Mono<Void> deleteAll();
}
