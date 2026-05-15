package com.food.eat.restaurantservice.service;

import com.food.eat.restaurantservice.dto.request.PromotionRequest;
import com.food.eat.restaurantservice.dto.response.PromotionResponse;

import java.util.List;

public interface PromotionService {

    PromotionResponse createPromotion(PromotionRequest request);

    PromotionResponse getPromotionById(Long promotionId);

    List<PromotionResponse> getPromotionsByRestaurant(Long restaurantId);

    PromotionResponse updatePromotion(Long promotionId, PromotionRequest request);

    void deletePromotion(Long promotionId);
}
