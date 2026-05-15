package com.food.eat.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("expires_in")
        long expiresIn,

        @JsonProperty("refresh_token")
        String refreshToken,

        String scope,

        UserResponse user
) {
    public AuthResponse(String tokenType, String accessToken, long expiresIn, UserResponse user) {
        this(tokenType, accessToken, expiresIn, null, null, user);
    }
}
