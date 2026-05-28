package com.food.eat.foodservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "food_images")
public class FoodImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodImageId;

    private String image;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
