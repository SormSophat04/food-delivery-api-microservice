package com.food.eat.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_items_id")
    private Long orderItemsId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long foodId;

    private Long quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private String customisation;
}
