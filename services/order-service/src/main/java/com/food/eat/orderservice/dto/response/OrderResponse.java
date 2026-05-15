package com.food.eat.orderservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        Long userId,
        Long restaurantId,
        Long deliveryAddress,
        String status,
        BigDecimal subtotal,
        BigDecimal deliveryFee,
        BigDecimal total,
        String notes,
        LocalDateTime placedAt,
        List<OrderItemResponse> items
) {}
