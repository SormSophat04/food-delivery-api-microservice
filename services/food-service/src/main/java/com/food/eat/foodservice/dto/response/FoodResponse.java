package com.food.eat.foodservice.dto.response;

public record FoodResponse(
        Long foodId,
        String name,
        Double price,
        String description,
        CategoryResponse category,
        Boolean available,
        String image
) {}
