package com.food.eat.gatewayserver.service.impl;

import com.food.eat.gatewayserver.dto.RouteApiRequest;
import com.food.eat.gatewayserver.dto.RouteApiResponse;
import com.food.eat.gatewayserver.entity.ApiRoute;
import com.food.eat.gatewayserver.exception.RouteNotFoundException;
import com.food.eat.gatewayserver.mapper.ApiRouteMapper;
import com.food.eat.gatewayserver.repository.ApiRouteRepository;
import com.food.eat.gatewayserver.service.GatewayRouteService;
import com.food.eat.gatewayserver.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final ApiRouteRepository apiRouteRepository;
    private final ApiRouteMapper apiRouteMapper;
    private final GatewayRouteService gatewayRouteService;

    @Override
    public Mono<RouteApiResponse> create(RouteApiRequest request) {
        ApiRoute apiRoute = apiRouteMapper.toApiRoute(request);
        apiRoute.setCreateBy("SYSTEM");
        apiRoute.setCreateAt(LocalDateTime.now());
        apiRoute.setUpdateAt(LocalDateTime.now());
        return apiRouteRepository.save(apiRoute)
                .doOnSuccess(ApiRoute -> gatewayRouteService.refreshRoutes())
                .map(apiRouteMapper::toRouteApiResponse)
                .onErrorMap(e -> {
                    log.error("Error creating route: {}", e.getMessage(), e);
                    return new RouteNotFoundException(e.getMessage());
                });
    }

    @Override
    public Mono<RouteApiResponse> update(RouteApiRequest request, Long id) {
        findById(id);
        Mono<ApiRoute> update = apiRouteRepository.save(apiRouteMapper.toApiRoute(request));
        return update.map(apiRouteMapper::toRouteApiResponse)
                .doOnSuccess(ApiRoute -> gatewayRouteService.refreshRoutes())
                .onErrorMap(e -> {
                    log.error("Error updating route: {}", e.getMessage(), e);
                    return new RouteNotFoundException(e.getMessage());
                });
    }

    @Override
    public Mono<RouteApiResponse> findById(Long id) {
        Mono<ApiRoute> byId = apiRouteRepository.findById(id);
        return byId.map(apiRouteMapper::toRouteApiResponse)
                .onErrorMap(e -> {
                    log.error("Error finding route: {}", e.getMessage(), e);
                    return new RouteNotFoundException(e.getMessage());
                });
    }

    @Override
    public Flux<RouteApiResponse> findAll() {
        Flux<ApiRoute> routeFlux = apiRouteRepository.findAll();
        return routeFlux.map(apiRouteMapper::toRouteApiResponse)
                .onErrorMap(e -> {
                    log.error("Error finding routes {}", e.getMessage(), e);
                    return new RouteNotFoundException(e.getMessage());
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        Mono<Void> byId = apiRouteRepository.deleteById(id);
        return byId.onErrorMap(e -> {
            log.info("Route has been deleted: {}", id);
            return new  RouteNotFoundException("Route has been deleted");
        });
    }

    @Override
    public Mono<Void> deleteAll() {
        Mono<Void> deleteAll = apiRouteRepository.deleteAll();
        return deleteAll.onErrorMap(e -> {
            log.error("Route has been deleted: {}", e.getMessage(), e);
            return new RouteNotFoundException("Route has been deleted");
        });
    }
}
