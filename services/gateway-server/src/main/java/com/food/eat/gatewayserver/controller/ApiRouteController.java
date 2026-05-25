package com.food.eat.gatewayserver.controller;

import com.food.eat.gatewayserver.dto.RouteApiRequest;
import com.food.eat.gatewayserver.dto.RouteApiResponse;
import com.food.eat.gatewayserver.exception.ApiResponse;
import com.food.eat.gatewayserver.service.GatewayRouteService;
import com.food.eat.gatewayserver.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/routes")
public class ApiRouteController {

    private final RouteService routeService;
    private final GatewayRouteService gatewayRouteService;

    @GetMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<Void>> refreshRoutes() {
        gatewayRouteService.refreshRoutes();
        return Mono.empty();
    }

    @PostMapping
    public Mono<ApiResponse<RouteApiResponse>> create(@RequestBody RouteApiRequest request){
        return routeService.create(request).map(ApiResponse::success);
    }

    @GetMapping
    public Flux<ApiResponse<RouteApiResponse>> findAll(){
        return routeService.findAll().map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<RouteApiResponse>> findById(@PathVariable Long id){
        return routeService.findById(id).map(ApiResponse::success);
    }

    @PutMapping("/{id}")
    public Mono<ApiResponse<RouteApiResponse>> update(@RequestBody RouteApiRequest request, @PathVariable Long id){
        return routeService.update(request, id).map(ApiResponse::success);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return routeService.deleteById(id);
    }

    @DeleteMapping
    public Mono<Void> deleteAll(){
        return routeService.deleteAll();
    }
}
