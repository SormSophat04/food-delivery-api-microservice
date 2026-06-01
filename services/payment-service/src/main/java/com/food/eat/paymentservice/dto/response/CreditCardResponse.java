package com.food.eat.paymentservice.dto.request;

public record CreditCardRequest(
        String cardNumber,
        String expiryMonth,
        String expiryYear,
        Integer cvv,
        String cardHolderName
) {
}
