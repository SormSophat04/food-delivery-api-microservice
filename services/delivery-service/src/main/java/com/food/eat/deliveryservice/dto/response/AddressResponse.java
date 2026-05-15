package com.food.eat.deliveryservice.dto.response;

import com.food.eat.deliveryservice.enums.AddressType;

public record AddressResponse(
        Long id,
        String fullName,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String district,
        String city,
        String province,
        String postalCode,
        String country,
        Double latitude,
        Double longitude,
        String landmark,
        AddressType addressType
) {
}
