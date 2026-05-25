package com.food.eat.gatewayserver.dto;

public record RouteApiRequest(
         String uri,
         String path,
         String method,
         String description,
         String groupCode,
         Integer rateLimit,
         Integer rateLimitDuration,
         String status
) {
}
