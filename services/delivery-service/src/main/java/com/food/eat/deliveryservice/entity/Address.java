package com.food.eat.deliveryservice.entity;

import com.food.eat.common.entity.BaseEntity;
import com.food.eat.deliveryservice.enums.AddressType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "addresses")
public class Address extends BaseEntity {

    @Id
    private String addressId;

    private Long userId;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "address_type")
    private String addressType;

    private Double lat;
    private Double lng;
}
