package com.food.eat.restaurantservice.entity;

import com.food.eat.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    private Long ownerId;

    private String name;
    private String description;
    private String phone;
    private Double lat;
    private Double lng;
    private String status;

    @Column(name = "opens_at")
    private String opensAt;

    @Column(name = "closes_at")
    private String closesAt;
}
