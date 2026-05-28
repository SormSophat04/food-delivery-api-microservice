package com.food.eat.foodservice.dto.response;

public record FoodStatusResponse(
        Long foodStatusId,
        Boolean isFeatured,
        Boolean isLiked
) {
}
