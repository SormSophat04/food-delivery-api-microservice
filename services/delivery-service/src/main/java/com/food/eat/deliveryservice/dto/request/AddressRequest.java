package com.food.eat.deliveryservice.dto.request;

import com.food.eat.deliveryservice.enums.AddressType;
import jakarta.validation.constraints.*;

public record AddressRequest(

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank
        @Size(max = 20)
        String phoneNumber,

        @NotBlank
        String addressLine1,

        String addressLine2,
        String district,

        @NotBlank
        String city,

        @NotBlank
        String province,

        String postalCode,

        @NotBlank
        String country,

        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        Double latitude,

        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        Double longitude,

        String landmark,

        @NotNull
        AddressType addressType

) {}