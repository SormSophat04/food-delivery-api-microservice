package com.food.eat.paymentservice.processor;

import com.food.eat.paymentservice.client.BakongClient;
import com.food.eat.paymentservice.dto.request.BakongKhqrRequest;
import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.BakongKhqrResponse;
import com.food.eat.paymentservice.dto.response.BakongTransactionResponse;
import com.food.eat.paymentservice.dto.response.PaymentResult;
import com.food.eat.paymentservice.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BakongPaymentProcessor implements PaymentProcessor {

    private final BakongClient bakongClient;

    @Override
    public PaymentResult createKhqr(PaymentRequest request) {
        BakongKhqrRequest bakongRequest = new BakongKhqrRequest(request.amount(), request.orderId());
        BakongKhqrResponse response = bakongClient.generateKhqr(bakongRequest);
        return new PaymentResult(response.qrContent(), response.transactionId(), PaymentStatus.PENDING);
    }

    @Override
    public PaymentStatus checkStatus(String transactionId) {
        BakongTransactionResponse response = bakongClient.checkTransaction(transactionId);
        return response.success() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
    }
}
