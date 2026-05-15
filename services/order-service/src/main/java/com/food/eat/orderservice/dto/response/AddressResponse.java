package com.food.eat.orderservice.dto.response;

public record AddressResponse(
        String id,
        String fullName,
        String phoneNumber,
        String addressLine1,
        String city,
        String province
) {}
