package com.food.eat.paymentservice.dto.response;

import com.food.eat.paymentservice.enums.PaymentStatus;

public record PaymentResult(
        String qrContent,
        String transactionId,
        PaymentStatus status
) {
}
