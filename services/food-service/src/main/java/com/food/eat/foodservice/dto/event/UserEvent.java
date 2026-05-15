package com.food.eat.foodservice.dto.event;

public record UserEvent(
        Long userId,
        String username,
        String email,
        String phoneNumber,
        String image,
        String role,
        boolean enabled
) {}
