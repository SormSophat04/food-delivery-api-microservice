package com.food.eat.deliveryservice.entity;

import com.food.eat.deliveryservice.enums.DriverType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "deliver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private DriverType driverType;

    @Column(name = "licence_plate")
    private String licencePlate;

    private String status;

    @Column(name = "current_lat")
    private Double currentLat;

    @Column(name = "current_lng")
    private Double currentLng;
}
