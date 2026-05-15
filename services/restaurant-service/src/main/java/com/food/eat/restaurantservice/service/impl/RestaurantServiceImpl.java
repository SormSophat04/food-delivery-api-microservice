package com.food.eat.restaurantservice.service.impl;

import com.food.eat.restaurantservice.client.AuthServiceClient;
import com.food.eat.restaurantservice.dto.request.RestaurantRequest;
import com.food.eat.restaurantservice.dto.response.RestaurantResponse;
import com.food.eat.restaurantservice.enitity.Restaurant;
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

    @Override
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        authServiceClient.getUserById(request.ownerId());

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(request.ownerId());
        restaurant.setName(request.name());
        restaurant.setDescription(request.description());
        restaurant.setPhone(request.phone());
        restaurant.setLat(request.lat());
        restaurant.setLng(request.lng());
        restaurant.setStatus(request.status() != null ? request.status() : "ACTIVE");
        restaurant.setOpensAt(request.opensAt());
        restaurant.setClosesAt(request.closesAt());

        Restaurant saved = restaurantRepository.save(restaurant);
        return toResponse(saved);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return toResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<RestaurantResponse> getRestaurantsByOwner(Long ownerId) {
        authServiceClient.getUserById(ownerId);
        return restaurantRepository.findByOwnerId(ownerId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        restaurant.setOwnerId(request.ownerId());
        restaurant.setName(request.name());
        restaurant.setDescription(request.description());
        restaurant.setPhone(request.phone());
        restaurant.setLat(request.lat());
        restaurant.setLng(request.lng());
        restaurant.setStatus(request.status());
        restaurant.setOpensAt(request.opensAt());
        restaurant.setClosesAt(request.closesAt());

        Restaurant saved = restaurantRepository.save(restaurant);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }

    private RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getRestaurantId(), restaurant.getOwnerId(),
                restaurant.getName(), restaurant.getDescription(),
                restaurant.getPhone(), restaurant.getLat(), restaurant.getLng(),
                restaurant.getStatus(), restaurant.getOpensAt(), restaurant.getClosesAt());
    }
}
