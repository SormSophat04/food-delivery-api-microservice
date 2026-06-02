package com.food.eat.paymentservice.dto.response;

public record CreditCardResponse(
        Long creditCardId,
        Long userId,
        String cardNumber,
        String expiryMonth,
        String expiryYear,
        Integer cvv,
        String cardHolderName
) {
}
