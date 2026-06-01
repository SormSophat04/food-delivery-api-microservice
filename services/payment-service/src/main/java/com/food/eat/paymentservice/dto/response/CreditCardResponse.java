package com.food.eat.paymentservice.dto.response;

public record CreditCardResponse(
        Long creditCardId,
        String cardNumber,
        String expiryMonth,
        String expiryYear,
        Integer cvv,
        String cardHolderName
) {
}
