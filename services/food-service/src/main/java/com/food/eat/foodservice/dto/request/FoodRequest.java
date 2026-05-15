package com.food.eat.foodservice.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record FoodRequest(
        @NotBlank String name,
        @NotNull @DecimalMin("0") Double price,
        String description,
        @NotNull Long categoryId,
        Boolean available,
        String image
) {}
