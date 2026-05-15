package com.food.eat.orderservice.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long orderItemsId,
        Long foodId,
        Long quantity,
        BigDecimal unitPrice,
        String customisation
) {}
