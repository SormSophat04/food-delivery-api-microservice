package com.food.eat.restaurantservice.controller;

import com.food.eat.restaurantservice.dto.request.RestaurantRequest;
import com.food.eat.restaurantservice.dto.response.CategoryResponse;
import com.food.eat.restaurantservice.dto.response.RestaurantResponse;
import com.food.eat.restaurantservice.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(request));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(
            @RequestParam(required = false) Long ownerId
    ) {
        if (ownerId != null) {
            return ResponseEntity.ok(restaurantService.getRestaurantsByOwner(ownerId));
        }
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @GetMapping("/{restaurantId}/categories")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getCategoriesByRestaurant(restaurantId));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long restaurantId,
            @Valid @RequestBody RestaurantRequest request
    ) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId, request));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
