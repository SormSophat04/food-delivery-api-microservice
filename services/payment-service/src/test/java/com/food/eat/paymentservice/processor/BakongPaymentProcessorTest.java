package com.food.eat.paymentservice.processor;

import com.food.eat.paymentservice.client.BakongClient;
import com.food.eat.paymentservice.dto.request.BakongKhqrRequest;
import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.BakongKhqrResponse;
import com.food.eat.paymentservice.dto.response.BakongTransactionResponse;
import com.food.eat.paymentservice.dto.response.PaymentResult;
import com.food.eat.paymentservice.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BakongPaymentProcessorTest {

    @Mock
    private BakongClient bakongClient;

    private BakongPaymentProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new BakongPaymentProcessor(bakongClient);
    }

    @Test
    void createKhqr_shouldCallBakongClientAndReturnPendingResult() {
        PaymentRequest paymentRequest = new PaymentRequest(new BigDecimal("25.50"), "order-123");
        BakongKhqrResponse bakongResponse = new BakongKhqrResponse("khqr-content-abc", "txn-001");
        when(bakongClient.generateKhqr(any(BakongKhqrRequest.class))).thenReturn(bakongResponse);

        PaymentResult result = processor.createKhqr(paymentRequest);

        assertThat(result.qrContent()).isEqualTo("khqr-content-abc");
        assertThat(result.transactionId()).isEqualTo("txn-001");
        assertThat(result.status()).isEqualTo(PaymentStatus.PENDING);

        ArgumentCaptor<BakongKhqrRequest> captor = ArgumentCaptor.forClass(BakongKhqrRequest.class);
        verify(bakongClient).generateKhqr(captor.capture());
        BakongKhqrRequest sentRequest = captor.getValue();
        assertThat(sentRequest.amount()).isEqualByComparingTo(new BigDecimal("25.50"));
        assertThat(sentRequest.orderId()).isEqualTo("order-123");
    }

    @Test
    void checkStatus_whenTransactionSuccessful_shouldReturnSuccess() {
        BakongTransactionResponse response = new BakongTransactionResponse("txn-001", true);
        when(bakongClient.checkTransaction("txn-001")).thenReturn(response);

        PaymentStatus status = processor.checkStatus("txn-001");

        assertThat(status).isEqualTo(PaymentStatus.SUCCESS);
        verify(bakongClient).checkTransaction("txn-001");
    }

    @Test
    void checkStatus_whenTransactionFails_shouldReturnFailed() {
        BakongTransactionResponse response = new BakongTransactionResponse("txn-002", false);
        when(bakongClient.checkTransaction("txn-002")).thenReturn(response);

        PaymentStatus status = processor.checkStatus("txn-002");

        assertThat(status).isEqualTo(PaymentStatus.FAILED);
        verify(bakongClient).checkTransaction("txn-002");
    }
}
