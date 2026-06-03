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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentProcessor paymentProcessor;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private OrderClient orderClient;

    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(paymentRepository, paymentProcessor, paymentMapper, orderClient);
    }

    @Test
    void createPayment_shouldCreateKhqrAndSavePayment() {
        PaymentRequest request = new PaymentRequest(new BigDecimal("99.99"), "order-456");
        PaymentResult paymentResult = new PaymentResult("qr-data", "txn-789", PaymentStatus.PENDING);

        Payment mappedPayment = new Payment();
        mappedPayment.setOrderId("order-456");
        mappedPayment.setAmount(new BigDecimal("99.99"));

        Payment savedPayment = new Payment();
        savedPayment.setPaymentId("payment-1");
        savedPayment.setOrderId("order-456");
        savedPayment.setAmount(new BigDecimal("99.99"));
        savedPayment.setPaymentMethod(PaymentMethod.KHQR);
        savedPayment.setStatus(PaymentStatus.PENDING);
        savedPayment.setQrContent("qr-data");
        savedPayment.setTransactionId("txn-789");

        PaymentResponse expectedResponse = new PaymentResponse(
                "payment-1", "order-456", new BigDecimal("99.99"),
                PaymentMethod.KHQR, PaymentStatus.PENDING, "qr-data", "txn-789",
                null, null, null, null
        );

        when(paymentProcessor.createKhqr(request)).thenReturn(paymentResult);
        when(paymentMapper.toEntity(request)).thenReturn(mappedPayment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(paymentMapper.toResponse(savedPayment)).thenReturn(expectedResponse);

        PaymentResponse response = paymentService.createPayment(request);

        assertThat(response).isSameAs(expectedResponse);

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(captor.capture());
        Payment captured = captor.getValue();

        assertThat(captured.getPaymentMethod()).isEqualTo(PaymentMethod.KHQR);
        assertThat(captured.getStatus()).isEqualTo(PaymentStatus.PENDING);
        assertThat(captured.getQrContent()).isEqualTo("qr-data");
        assertThat(captured.getTransactionId()).isEqualTo("txn-789");
        assertThat(captured.getExpiredAt()).isNotNull();
        assertThat(captured.getExpiredAt()).isAfter(LocalDateTime.now());
    }

    @Test
    void getPayment_whenExists_shouldReturnPayment() {
        Payment payment = new Payment();
        payment.setPaymentId("1");
        PaymentResponse expectedResponse = new PaymentResponse(
                "1", null, null, null, null, null, null, null, null, null, null
        );

        when(paymentRepository.findById("1")).thenReturn(Optional.of(payment));
        when(paymentMapper.toResponse(payment)).thenReturn(expectedResponse);

        PaymentResponse response = paymentService.getPayment(1L);

        assertThat(response).isSameAs(expectedResponse);
        verify(paymentRepository).findById("1");
    }

    @Test
    void getPayment_whenNotExists_shouldThrowException() {
        when(paymentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.getPayment(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Payment not found");

        verify(paymentRepository).findById("999");
    }

    @Test
    void confirmPayment_whenPending_shouldConfirm() {
        Payment payment = new Payment();
        payment.setPaymentId("1");
        payment.setOrderId("456");
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = new Payment();
        savedPayment.setPaymentId("1");
        savedPayment.setOrderId("456");
        savedPayment.setStatus(PaymentStatus.SUCCESS);

        PaymentResponse expectedResponse = new PaymentResponse(
                "1", "456", null, null, PaymentStatus.SUCCESS, null, null, null, null, null, null
        );

        when(paymentRepository.findById("1")).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(paymentMapper.toResponse(savedPayment)).thenReturn(expectedResponse);

        PaymentResponse response = paymentService.confirmPayment(1L);

        assertThat(response).isSameAs(expectedResponse);
        verify(orderClient).markOrderAsPaid(456L);
    }

    @Test
    void confirmPayment_whenNotPending_shouldThrow() {
        Payment payment = new Payment();
        payment.setPaymentId("1");
        payment.setStatus(PaymentStatus.FAILED);

        when(paymentRepository.findById("1")).thenReturn(Optional.of(payment));

        assertThatThrownBy(() -> paymentService.confirmPayment(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Payment cannot be confirmed");
    }

    @Test
    void confirmPayment_whenNotExists_shouldThrow() {
        when(paymentRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.confirmPayment(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Payment not found");
    }
}
