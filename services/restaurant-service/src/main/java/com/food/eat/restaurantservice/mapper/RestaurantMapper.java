package com.food.eat.restaurantservice.mapper;

import com.food.eat.restaurantservice.dto.request.RestaurantRequest;
import com.food.eat.restaurantservice.dto.response.RestaurantResponse;
import com.food.eat.restaurantservice.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "status", defaultValue = "ACTIVE")
    Restaurant toEntity(RestaurantRequest request);

    RestaurantResponse toResponse(Restaurant restaurant);

    void updateEntity(RestaurantRequest request, @MappingTarget Restaurant restaurant);
}
