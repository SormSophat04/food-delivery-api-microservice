package com.food.eat.gatewayserver.dto;

import java.time.LocalDateTime;

public record RouteApiResponse(
         String url,
         String path,
         String method,
         String description,
         String groupCode,
         Integer reteLimit,
         Integer rateLimitDuration,
         String status,
         
         LocalDateTime createTime,
         String createBy,
         LocalDateTime updateTime,
         String updateBy
) {
}
