package com.food.eat.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 2, max = 50)
        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 15)
        String phoneNumber,

        @NotBlank
        @Size(min = 8, max = 72)
        String password
) {
}
