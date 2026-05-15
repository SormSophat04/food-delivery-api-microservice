package com.food.eat.orderservice.dto.response;

public record RestaurantResponse(
        Long restaurantId,
        String name,
        String description,
        String status
) {}
