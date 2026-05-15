package com.food.eat.restaurantservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequest(
        @NotNull Long ownerId,
        @NotBlank String name,
        String description,
        String phone,
        Double lat,
        Double lng,
        String status,
        String opensAt,
        String closesAt
) {}
