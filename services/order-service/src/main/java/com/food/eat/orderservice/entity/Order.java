package com.food.eat.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    private Long userId;
    private Long restaurantId;
    @Column(name = "delivery_address_id")
    private Long deliveryAddress;

    private String status;
    private BigDecimal subtotal;

    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;
    private BigDecimal total;
    private String notes;

    @Column(name = "place_at")
    private LocalDateTime placedAt;

    @Column(name = "delivery_at")
    private LocalDateTime deliveredAt;
}
