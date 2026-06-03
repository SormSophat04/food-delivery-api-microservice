package com.food.eat.paymentservice.dto.response;

import com.food.eat.paymentservice.enums.PaymentMethod;
import com.food.eat.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        String qrContent,
        String transactionId,
        LocalDateTime expiredAt,
        LocalDateTime paidAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
