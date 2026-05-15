package com.food.eat.restaurantservice.dto.event;

public record UserEvent(
        Long userId,
        String username,
        String email,
        String phoneNumber,
        String image,
        String role,
        boolean enabled
) {}
