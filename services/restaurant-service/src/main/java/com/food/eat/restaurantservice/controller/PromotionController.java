package com.food.eat.restaurantservice.controller;

import com.food.eat.restaurantservice.dto.request.PromotionRequest;
import com.food.eat.restaurantservice.dto.response.PromotionResponse;
import com.food.eat.restaurantservice.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<PromotionResponse> createPromotion(@Valid @RequestBody PromotionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionService.createPromotion(request));
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getPromotions(
            @RequestParam Long restaurantId
    ) {
        return ResponseEntity.ok(promotionService.getPromotionsByRestaurant(restaurantId));
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable Long promotionId) {
        return ResponseEntity.ok(promotionService.getPromotionById(promotionId));
    }

    @PutMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> updatePromotion(
            @PathVariable Long promotionId,
            @Valid @RequestBody PromotionRequest request
    ) {
        return ResponseEntity.ok(promotionService.updatePromotion(promotionId, request));
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId) {
        promotionService.deletePromotion(promotionId);
        return ResponseEntity.noContent().build();
    }
}
