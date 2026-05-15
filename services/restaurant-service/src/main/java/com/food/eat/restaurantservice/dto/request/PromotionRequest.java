package com.food.eat.restaurantservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public record PromotionRequest(
        @NotNull Long restaurantId,
        @NotBlank String code,
        @NotBlank String type,
        @NotNull BigDecimal value,
        @NotNull Date validFrom,
        @NotNull Date validTo,
        Long usesRemaining
) {}
