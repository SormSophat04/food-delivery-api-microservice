package com.food.eat.foodservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private Long restaurantId;

    @Column(name = "display_order")
    private Long displayOrder;

    @Column(name = "category_name")
    private String categoryName;

    private String image;
}
