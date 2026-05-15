package com.food.eat.foodservice.dto.response;

import java.math.BigDecimal;

public record FoodOptionResponse(
        Long foodOptionId,
        String name,
        BigDecimal extraPrice
) {}
