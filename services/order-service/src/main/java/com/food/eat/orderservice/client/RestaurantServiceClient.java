package com.food.eat.orderservice.client;

import com.food.eat.orderservice.dto.response.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", path = "/api/restaurants")
public interface RestaurantServiceClient {

    @GetMapping("/{restaurantId}")
    RestaurantResponse getRestaurantById(@PathVariable Long restaurantId);
}
