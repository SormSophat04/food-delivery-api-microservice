package com.food.eat.paymentservice.mapper;

import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.PaymentResponse;
import com.food.eat.paymentservice.entity.Payment;
import com.food.eat.paymentservice.enums.PaymentMethod;
import com.food.eat.paymentservice.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMapperTest {

    private PaymentMapperImpl paymentMapper;

    @BeforeEach
    void setUp() {
        paymentMapper = new PaymentMapperImpl();
    }

    @Test
    void toEntity_shouldMapFields() {
        PaymentRequest request = new PaymentRequest(new BigDecimal("15.00"), "order-001");

        Payment payment = paymentMapper.toEntity(request);

        assertThat(payment).isNotNull();
        assertThat(payment.getOrderId()).isEqualTo("order-001");
        assertThat(payment.getAmount()).isEqualByComparingTo(new BigDecimal("15.00"));
        assertThat(payment.getPaymentId()).isNull();
        assertThat(payment.getStatus()).isNull();
        assertThat(payment.getQrContent()).isNull();
        assertThat(payment.getTransactionId()).isNull();
    }

    @Test
    void toResponse_shouldMapAllFields() {
        Payment payment = new Payment();
        payment.setPaymentId("pay-123");
        payment.setOrderId("order-001");
        payment.setAmount(new BigDecimal("15.00"));
        payment.setPaymentMethod(PaymentMethod.KHQR);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setQrContent("qr-data");
        payment.setTransactionId("txn-001");
        payment.setExpiredAt(LocalDateTime.of(2026, 6, 3, 16, 0));
        payment.setPaidAt(LocalDateTime.of(2026, 6, 3, 15, 45));
        payment.setCreatedAt(LocalDateTime.of(2026, 6, 3, 15, 30));
        payment.setUpdatedAt(LocalDateTime.of(2026, 6, 3, 15, 45));

        PaymentResponse response = paymentMapper.toResponse(payment);

        assertThat(response).isNotNull();
        assertThat(response.paymentId()).isEqualTo("pay-123");
        assertThat(response.orderId()).isEqualTo("order-001");
        assertThat(response.amount()).isEqualByComparingTo(new BigDecimal("15.00"));
        assertThat(response.paymentMethod()).isEqualTo(PaymentMethod.KHQR);
        assertThat(response.status()).isEqualTo(PaymentStatus.SUCCESS);
        assertThat(response.qrContent()).isEqualTo("qr-data");
        assertThat(response.transactionId()).isEqualTo("txn-001");
        assertThat(response.expiredAt()).isEqualTo(LocalDateTime.of(2026, 6, 3, 16, 0));
        assertThat(response.paidAt()).isEqualTo(LocalDateTime.of(2026, 6, 3, 15, 45));
        assertThat(response.createdAt()).isEqualTo(LocalDateTime.of(2026, 6, 3, 15, 30));
        assertThat(response.updatedAt()).isEqualTo(LocalDateTime.of(2026, 6, 3, 15, 45));
    }

    @Test
    void toRequest_shouldMapFields() {
        Payment payment = new Payment();
        payment.setOrderId("order-002");
        payment.setAmount(new BigDecimal("42.00"));

        PaymentRequest request = paymentMapper.toRequest(payment);

        assertThat(request).isNotNull();
        assertThat(request.orderId()).isEqualTo("order-002");
        assertThat(request.amount()).isEqualByComparingTo(new BigDecimal("42.00"));
    }
}
