package com.food.eat.restaurantservice.service;

import com.food.eat.restaurantservice.dto.request.RestaurantRequest;
import com.food.eat.restaurantservice.dto.response.CategoryResponse;
import com.food.eat.restaurantservice.dto.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse createRestaurant(RestaurantRequest request);

    RestaurantResponse getRestaurantById(Long restaurantId);

    List<RestaurantResponse> getAllRestaurants();

    List<RestaurantResponse> getRestaurantsByOwner(Long ownerId);

    RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request);

    void deleteRestaurant(Long restaurantId);

    List<CategoryResponse> getCategoriesByRestaurant(Long restaurantId);
}
