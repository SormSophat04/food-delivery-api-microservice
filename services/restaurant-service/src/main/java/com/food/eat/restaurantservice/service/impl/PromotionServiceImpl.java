package com.food.eat.restaurantservice.service.impl;

import com.food.eat.restaurantservice.dto.request.PromotionRequest;
import com.food.eat.restaurantservice.dto.response.PromotionResponse;
import com.food.eat.restaurantservice.entity.Promotion;
import com.food.eat.restaurantservice.mapper.PromotionMapper;
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
    private final PromotionMapper promotionMapper;

    @Override
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        Promotion promotion = promotionMapper.toEntity(request);
        Promotion saved = promotionRepository.save(promotion);
        return promotionMapper.toResponse(saved);
    }

    @Override
    public PromotionResponse getPromotionById(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));
        return promotionMapper.toResponse(promotion);
    }

    @Override
    public List<PromotionResponse> getPromotionsByRestaurant(Long restaurantId) {
        return promotionRepository.findByRestaurantId(restaurantId).stream()
                .map(promotionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));

        promotionMapper.updateEntity(request, promotion);

        Promotion saved = promotionRepository.save(promotion);
        return promotionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));
        promotionRepository.delete(promotion);
    }

}
