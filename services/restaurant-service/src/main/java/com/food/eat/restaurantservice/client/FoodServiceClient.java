package com.food.eat.restaurantservice.client;

import com.food.eat.restaurantservice.dto.response.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "food-service", path = "/api/categories")
public interface FoodServiceClient {

    @GetMapping
    List<CategoryResponse> getCategoriesByRestaurant(@RequestParam("restaurantId") Long restaurantId);
}
