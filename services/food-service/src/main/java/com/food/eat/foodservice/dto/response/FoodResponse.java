package com.food.eat.foodservice.dto.response;

import java.util.List;

public record FoodResponse(
        Long foodId,
        String name,
        String description,
        Boolean available,
        PriceResponse price,
        CategoryResponse category,
        List<String> image,
        List<FoodOptionResponse> foodOption,
        List<FoodStatusResponse> foodStatus
) {}
