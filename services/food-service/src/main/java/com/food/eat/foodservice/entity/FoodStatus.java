package com.food.eat.foodservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "food")
@Table(name = "food_status")
public class FoodStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_status_id")
    private Long foodStatusId;

    private Boolean isFeatured;

    private Boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
