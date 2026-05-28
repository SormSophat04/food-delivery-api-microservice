package com.food.eat.restaurantservice.mapper;

import com.food.eat.restaurantservice.dto.request.PromotionRequest;
import com.food.eat.restaurantservice.dto.response.PromotionResponse;
import com.food.eat.restaurantservice.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    @Mapping(target = "uses_remaining", source = "usesRemaining")
    Promotion toEntity(PromotionRequest request);

    @Mapping(target = "usesRemaining", source = "uses_remaining")
    PromotionResponse toResponse(Promotion promotion);

    @Mapping(target = "uses_remaining", source = "usesRemaining")
    void updateEntity(PromotionRequest request, @MappingTarget Promotion promotion);
}
