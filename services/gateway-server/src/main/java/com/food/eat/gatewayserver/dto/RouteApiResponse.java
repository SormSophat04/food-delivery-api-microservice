package com.food.eat.gatewayserver.dto;

import java.time.LocalDateTime;

public record RouteApiResponse(
        Long id,
        String uri,
        String path,
        String method,
        String description,
        String groupCode,
        Integer rateLimit,
        Integer rateLimitDuration,
        String status,

        LocalDateTime createAt,
        String createBy,
        LocalDateTime updateAt,
        String updateBy
) {
}
