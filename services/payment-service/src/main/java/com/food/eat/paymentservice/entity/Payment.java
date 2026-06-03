package com.food.eat.paymentservice.entity;

import com.food.eat.common.entity.BaseEntity;
import com.food.eat.paymentservice.enums.PaymentMethod;
import com.food.eat.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    /**
     * KHQR string returned by provider
     */
    @Column(columnDefinition = "TEXT")
    private String qrContent;

    /**
     * Transaction reference from bank/provider
     */
    private String transactionId;

    /**
     * Merchant account identifier
     */
    private String merchantId;

    /**
     * Optional payment description
     */
    private String description;

    /**
     * When payment expires
     */
    private LocalDateTime expiredAt;

    /**
     * When payment is completed
     */
    private LocalDateTime paidAt;

}
