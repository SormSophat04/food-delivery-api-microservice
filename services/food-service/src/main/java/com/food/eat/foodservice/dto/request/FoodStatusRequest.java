package com.food.eat.foodservice.dto.request;

public record FoodStatusRequest(
        Boolean isFeatured,
        Boolean isLiked
) {}
