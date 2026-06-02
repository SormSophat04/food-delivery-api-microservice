package com.food.eat.paymentservice.dto.request;

public record CreditCardRequest(
        Long userId,
        String cardNumber,
        String expiryMonth,
        String expiryYear,
        Integer cvv,
        String cardHolderName
) {
}
