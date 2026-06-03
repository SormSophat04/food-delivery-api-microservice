package com.food.eat.paymentservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreditCardRequest(
        Long userId,

        @NotNull
        Integer cardNumber,

        @NotNull @Min(1) @Max(12)
        Integer expiryMonth,

        @Min(0) @Max(99)
        Integer expiryYear,

        @NotNull @Min(100) @Max(9999)
        Integer cvv,

        String cardHolderName
) {
}
