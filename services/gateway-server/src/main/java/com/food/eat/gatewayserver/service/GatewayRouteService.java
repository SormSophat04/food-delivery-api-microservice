package com.food.eat.gatewayserver.service;

import com.food.eat.gatewayserver.dto.RouteApiRequest;
import com.food.eat.gatewayserver.dto.RouteApiResponse;
import reactor.core.publisher.Mono;

public interface GatewayRouteService {
     void refreshRoutes();
}
