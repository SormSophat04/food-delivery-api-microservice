package com.food.eat.orderservice.entity;

import com.food.eat.common.entity.BaseEntity;
import com.food.eat.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    private Long userId;

    private Long restaurantId;

    @Column(name = "delivery_address_id")
    private Long deliveryAddress;

    private BigDecimal subtotal;

    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;

    private BigDecimal total;

    private String notes;

    private OrderStatus orderStatus;

    @Column(name = "place_at")
    private LocalDateTime placedAt;

    @Column(name = "delivery_at")
    private LocalDateTime deliveredAt;
}
