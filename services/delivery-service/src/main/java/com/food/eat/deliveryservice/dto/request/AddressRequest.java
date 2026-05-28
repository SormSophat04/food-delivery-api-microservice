package com.food.eat.deliveryservice.dto.request;

import jakarta.validation.constraints.*;

public record AddressRequest(

        @NotBlank
        String addressDetail,

        @NotBlank
        String addressType,

        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        Double lat,

        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        Double lng

) {}