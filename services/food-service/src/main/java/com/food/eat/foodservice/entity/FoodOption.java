package com.food.eat.foodservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "food_options")
public class FoodOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_option_id")
    private Long foodOptionId;

    private String name;

    @Column(name = "extra_price")
    private BigDecimal extraPrice;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
