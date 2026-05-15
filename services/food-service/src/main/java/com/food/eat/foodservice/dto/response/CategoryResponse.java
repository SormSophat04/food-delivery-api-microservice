package com.food.eat.foodservice.dto.response;

public record CategoryResponse(
        Long categoryId,
        String categoryName,
        Long restaurantId,
        Long displayOrder,
        String image
) {}
