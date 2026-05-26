package com.food.eat.foodservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food_status")
public class FoodStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_status_id")
    private Long foodStatusId;

    private Boolean isFeatured;

    private Boolean isLiked;
}
