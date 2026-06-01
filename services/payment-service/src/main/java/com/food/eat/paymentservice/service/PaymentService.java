package com.food.eat.paymentservice.service;

import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest paymentRequest);
    PaymentResponse getPayment(Long paymentId);
    PaymentResponse confirmPayment(Long paymentId);
}
