package com.food.eat.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        @NotNull Long restaurantId,
        @NotNull Long deliveryAddress,
        String notes,
        @NotNull List<OrderItemRequest> items
) {}
