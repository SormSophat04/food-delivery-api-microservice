package com.food.eat.orderservice.client;

import com.food.eat.orderservice.dto.response.FoodResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "food-service", path = "/api/foods")
public interface FoodServiceClient {

    @GetMapping("/{foodId}")
    FoodResponse getFoodById(@PathVariable Long foodId);
}
