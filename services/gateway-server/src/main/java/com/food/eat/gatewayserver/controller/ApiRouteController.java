package com.food.eat.gatewayserver;

import com.food.eat.gatewayserver.dto.RouteApiRequest;
import com.food.eat.gatewayserver.dto.RouteApiResponse;
import com.food.eat.gatewayserver.exception.ApiResponse;
import com.food.eat.gatewayserver.service.GatewayRouteService;
import com.food.eat.gatewayserver.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ApiRouteController {

    private final RouteService routeService;
    private final GatewayRouteService gatewayRouteService;

    @PostMapping
    public Mono<ApiResponse<RouteApiResponse>> create(@RequestBody RouteApiRequest request){
        return 
    }
}
