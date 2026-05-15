package com.food.eat.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyCodeRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "^\\d{4,8}$", message = "code must contain 4 to 8 digits")
        String code
) {
}
