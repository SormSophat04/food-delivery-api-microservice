package com.food.eat.restaurantservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record PromotionResponse(
        Long promotionId,
        Long restaurantId,
        String code,
        String type,
        BigDecimal value,
        Date validFrom,
        Date validTo,
        Long usesRemaining,
        LocalDateTime createdAt
) {}
