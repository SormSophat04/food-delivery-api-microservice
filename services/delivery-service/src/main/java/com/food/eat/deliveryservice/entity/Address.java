package com.food.eat.deliveryservice.entity;

import com.food.eat.common.entity.BaseEntity;
import com.food.eat.deliveryservice.enums.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

    private String name;
    private String phone;
    private String address;
    private String district;
    private String city;
    private String province;
    private String postalCode;
    private String country;

    private Double latitude;
    private Double longitude;

    private String landmark;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
