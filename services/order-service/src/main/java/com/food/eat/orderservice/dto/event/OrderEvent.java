package com.food.eat.orderservice.dto.event;

import java.math.BigDecimal;

public record OrderEvent(
        Long orderId,
        Long userId,
        Long restaurantId,
        String status,
        BigDecimal total
) {}
