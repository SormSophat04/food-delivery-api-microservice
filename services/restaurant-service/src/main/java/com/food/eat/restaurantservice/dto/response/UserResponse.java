package com.food.eat.restaurantservice.dto.response;

public record UserResponse(
        Long userId,
        String username,
        String email,
        String phoneNumber,
        String image,
        String role,
        boolean enabled
) {}
