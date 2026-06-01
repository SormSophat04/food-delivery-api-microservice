package com.food.eat.paymentservice.processor;

import com.food.eat.paymentservice.dto.request.BakongKhqrRequest;
import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.PaymentResult;
import com.food.eat.paymentservice.enums.PaymentStatus;

public interface PaymentProcessor {
    PaymentResult createKhqr(
            PaymentRequest request
    );

    PaymentStatus checkStatus(
            String transactionId
    );
}
