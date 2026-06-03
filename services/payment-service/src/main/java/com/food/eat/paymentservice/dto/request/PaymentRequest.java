package com.food.eat.paymentservice.dto.request;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        Long orderId
) {
}
