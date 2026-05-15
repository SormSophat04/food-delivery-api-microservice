package com.food.eat.orderservice.dto.response;

public record FoodResponse(
        Long foodId,
        String name,
        Double price,
        String description,
        Boolean available
) {}
