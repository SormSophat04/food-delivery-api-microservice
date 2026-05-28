package com.food.eat.foodservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FoodOptionRequest(
        @NotBlank String name,
        @NotNull BigDecimal extraPrice
) {
}
