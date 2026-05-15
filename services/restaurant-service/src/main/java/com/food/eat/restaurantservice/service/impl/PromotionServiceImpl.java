package com.food.eat.restaurantservice.service.impl;

import com.food.eat.restaurantservice.dto.request.PromotionRequest;
import com.food.eat.restaurantservice.dto.response.PromotionResponse;
import com.food.eat.restaurantservice.enitity.Promotion;
import com.food.eat.restaurantservice.repository.PromotionRepository;
import com.food.eat.restaurantservice.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        Promotion promotion = new Promotion();
        promotion.setRestaurantId(request.restaurantId());
        promotion.setCode(request.code());
        promotion.setType(request.type());
        promotion.setValue(request.value());
        promotion.setValidFrom(request.validFrom());
        promotion.setValidTo(request.validTo());
        promotion.setUses_remaining(request.usesRemaining());

        Promotion saved = promotionRepository.save(promotion);
        return toResponse(saved);
    }

    @Override
    public PromotionResponse getPromotionById(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));
        return toResponse(promotion);
    }

    @Override
    public List<PromotionResponse> getPromotionsByRestaurant(Long restaurantId) {
        return promotionRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));

        promotion.setRestaurantId(request.restaurantId());
        promotion.setCode(request.code());
        promotion.setType(request.type());
        promotion.setValue(request.value());
        promotion.setValidFrom(request.validFrom());
        promotion.setValidTo(request.validTo());
        promotion.setUses_remaining(request.usesRemaining());

        Promotion saved = promotionRepository.save(promotion);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));
        promotionRepository.delete(promotion);
    }

    private PromotionResponse toResponse(Promotion promotion) {
        return new PromotionResponse(
                promotion.getPromotionId(), promotion.getRestaurantId(),
                promotion.getCode(), promotion.getType(), promotion.getValue(),
                promotion.getValidFrom(), promotion.getValidTo(), promotion.getUses_remaining());
    }
}
