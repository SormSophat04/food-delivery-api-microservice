package com.food.eat.restaurantservice.service.impl;

import com.food.eat.restaurantservice.client.AuthServiceClient;
import com.food.eat.restaurantservice.client.FoodServiceClient;
import com.food.eat.restaurantservice.dto.request.RestaurantRequest;
import com.food.eat.restaurantservice.dto.response.CategoryResponse;
import com.food.eat.restaurantservice.dto.response.RestaurantResponse;
import com.food.eat.restaurantservice.entity.Restaurant;
import com.food.eat.restaurantservice.mapper.RestaurantMapper;
import com.food.eat.restaurantservice.repository.RestaurantRepository;
import com.food.eat.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AuthServiceClient authServiceClient;
    private final FoodServiceClient foodServiceClient;
    private final RestaurantMapper restaurantMapper;

    @Override
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        authServiceClient.getUserById(request.ownerId());

        Restaurant restaurant = restaurantMapper.toEntity(request);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(saved);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return restaurantMapper.toResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponse)
                .toList();
    }

    @Override
    public List<RestaurantResponse> getRestaurantsByOwner(Long ownerId) {
        authServiceClient.getUserById(ownerId);
        return restaurantRepository.findByOwnerId(ownerId).stream()
                .map(restaurantMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        restaurantMapper.updateEntity(request, restaurant);

        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(saved);
    }

    @Override
    public List<CategoryResponse> getCategoriesByRestaurant(Long restaurantId) {
        getRestaurantById(restaurantId);
        return foodServiceClient.getCategoriesByRestaurant(restaurantId);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }

}
