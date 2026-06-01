package com.food.eat.paymentservice.dto.request;

import java.math.BigDecimal;

public record BakongKhqrRequest(
        BigDecimal amount,
        String orderId
) {
}
