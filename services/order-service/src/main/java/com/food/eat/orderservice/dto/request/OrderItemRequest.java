package com.food.eat.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderItemRequest(
        @NotNull Long foodId,
        @NotNull @Positive Long quantity,
        @NotNull BigDecimal unitPrice,
        String customisation
) {}
