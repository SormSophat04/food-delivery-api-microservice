package com.food.eat.deliveryservice.dto.response;

public record AddressResponse(
        String id,
        String addressDetail,
        String addressType,
        Double lat,
        Double lng
) {
}
