package com.food.eat.deliveryservice.entity;

import com.food.eat.deliveryservice.enums.DeliverType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "deliver")
public class DeliveryDriver {

    @Id
    private Long id;
    private Long userId;
    private Long deliveryId;
    private DeliverType deliverType;
}
