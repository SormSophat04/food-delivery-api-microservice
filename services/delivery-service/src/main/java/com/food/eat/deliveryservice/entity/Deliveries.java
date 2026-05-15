package com.food.eat.deliveryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "deliveries")
public class Deliveries {

    @Id
    private String deliveryId;

    private Long orderId;
    private Long driverId;
    private String status;

    @Column(name = "pickup_at")
    private LocalDateTime pickupAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

}
