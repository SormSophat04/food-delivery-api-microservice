package com.food.eat.foodservice.service;

import com.food.eat.foodservice.dto.request.FoodRequest;
import com.food.eat.foodservice.dto.response.FoodResponse;
import com.food.eat.foodservice.dto.response.FoodOptionResponse;

import java.util.List;

public interface FoodService {
    FoodResponse createFood(FoodRequest request);
    FoodResponse getFoodById(Long foodId);
    List<FoodResponse> getAllFoods();
    List<FoodResponse> getFoodsByCategory(Long categoryId);
    FoodResponse updateFood(Long foodId, FoodRequest request);
    void deleteFood(Long foodId);
    List<FoodOptionResponse> getFoodOptions(Long foodId);
}
