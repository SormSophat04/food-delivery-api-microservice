package com.food.eat.foodservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record PriceRequest(
        @NotNull @DecimalMin("0.01") Double originalPrice,
        @DecimalMin("0.01") Double discountPrice
) {}
