package com.food.eat.foodservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank String categoryName,
        Long restaurantId,
        Long displayOrder,
        String image
) {}
