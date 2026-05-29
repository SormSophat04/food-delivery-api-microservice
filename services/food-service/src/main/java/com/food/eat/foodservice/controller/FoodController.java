package com.food.eat.foodservice.controller;

import com.food.eat.foodservice.dto.request.FoodRequest;
import com.food.eat.foodservice.dto.response.FoodOptionResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import com.food.eat.foodservice.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<FoodResponse> createFood(@Valid @RequestBody FoodRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(request));
    }

    @GetMapping
    public Page<FoodResponse> getFoods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        return foodService.getFoods(page, size);
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable Long foodId) {
        return ResponseEntity.ok(foodService.getFoodById(foodId));
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<FoodResponse> updateFood(
            @PathVariable Long foodId,
            @Valid @RequestBody FoodRequest request
    ) {
        return ResponseEntity.ok(foodService.updateFood(foodId, request));
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.noContent().build();
    }
}
