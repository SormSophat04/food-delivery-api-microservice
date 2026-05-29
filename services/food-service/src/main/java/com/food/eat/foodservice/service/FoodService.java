package com.food.eat.foodservice.service;

import com.food.eat.foodservice.dto.request.FoodRequest;
import com.food.eat.foodservice.dto.response.FoodOptionResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodService {
    FoodResponse createFood(FoodRequest request);
    FoodResponse getFoodById(Long foodId);
    Page<FoodResponse> getFoods(int page, int size);
    FoodResponse updateFood(Long foodId, FoodRequest request);
    void deleteFood(Long foodId);
}
