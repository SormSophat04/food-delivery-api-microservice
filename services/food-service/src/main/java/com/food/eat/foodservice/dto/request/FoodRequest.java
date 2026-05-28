package com.food.eat.foodservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FoodRequest(
        @NotBlank String name,
        String description,
        Boolean available,
        @NotNull PriceRequest price,
        @NotNull Long categoryId,
        List<String> image,
        List<FoodOptionRequest> foodOption,
        List<FoodStatusRequest> foodStatus
) {}
