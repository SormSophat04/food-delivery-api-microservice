package com.food.eat.restaurantservice.dto.response;

import java.time.LocalDateTime;

public record RestaurantResponse(
        Long restaurantId,
        Long ownerId,
        String name,
        String description,
        String phone,
        Double lat,
        Double lng,
        String status,
        String opensAt,
        String closesAt,
        LocalDateTime createdAt
) {}
