package com.food.eat.deliveryservice.dto.response;

import com.food.eat.deliveryservice.enums.AddressType;

public record AddressResponse(
        String id,
        String addressDetail,
        AddressType addressType,
        Double lat,
        Double lng
) {
}
