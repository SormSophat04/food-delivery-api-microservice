package com.food.eat.paymentservice.dto.response;

public record CreditCardResponse(
        Long creditCardId,
        Long userId,
        Integer cardNumber,
        Integer expiryMonth,
        Integer expiryYear,
        Integer cvv,
        String cardHolderName
) {
}
