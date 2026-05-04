package com.food.eat.authservice.dto.response;

public record AuthResponse(
        String tokenType,
        String accessToken,
        long expiresInMs,
        UserResponse user
) {
}
