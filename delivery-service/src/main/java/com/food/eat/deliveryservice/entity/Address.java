package com.food.eat.deliveryservice.entity;

import com.food.eat.common.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "addresses")
public class Address extends BaseEntity {

    @Id
    private String addressId;
    private String addressName;
    private String addressDetail;
    private String phoneNumber;
}
