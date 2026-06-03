package com.food.eat.paymentservice.service.impl;

import com.food.eat.paymentservice.client.OrderClient;
import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.PaymentResponse;
import com.food.eat.paymentservice.dto.response.PaymentResult;
import com.food.eat.paymentservice.entity.Payment;
import com.food.eat.paymentservice.enums.PaymentMethod;
import com.food.eat.paymentservice.enums.PaymentStatus;
import com.food.eat.paymentservice.mapper.PaymentMapper;
import com.food.eat.paymentservice.processor.PaymentProcessor;
import com.food.eat.paymentservice.repository.PaymentRepository;
import com.food.eat.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;
    private final PaymentMapper paymentMapper;
    private final OrderClient orderClient;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        PaymentResult paymentResult = paymentProcessor.createKhqr(paymentRequest);
        Payment payment = paymentMapper.toEntity(paymentRequest);

        payment.setPaymentMethod(PaymentMethod.KHQR);
        payment.setStatus(paymentResult.status());
        payment.setQrContent(paymentResult.qrContent());
        payment.setTransactionId(paymentResult.transactionId());
        payment.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        Payment save = paymentRepository.save(payment);
        return paymentMapper.toResponse(save);
    }

    @Override
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId.toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse confirmPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId.toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payment cannot be confirmed. Current status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);

        orderClient.markOrderAsPaid(Long.valueOf(saved.getOrderId()));

        return paymentMapper.toResponse(saved);
    }
}
