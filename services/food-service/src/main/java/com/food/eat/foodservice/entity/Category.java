package com.food.eat.foodservice.entity;

import com.food.eat.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

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
