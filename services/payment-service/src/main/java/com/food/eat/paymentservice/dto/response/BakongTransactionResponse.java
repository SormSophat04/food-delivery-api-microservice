package com.food.eat.paymentservice.dto.response;

public record BakongTransactionResponse(
        String transactionId,
        boolean success
) {
}
