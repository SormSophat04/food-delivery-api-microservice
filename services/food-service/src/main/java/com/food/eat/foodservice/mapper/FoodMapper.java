package com.food.eat.foodservice.mapper;

import com.food.eat.foodservice.dto.request.FoodOptionRequest;
import com.food.eat.foodservice.dto.request.FoodRequest;
import com.food.eat.foodservice.dto.request.FoodStatusRequest;
import com.food.eat.foodservice.dto.request.PriceRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;
import com.food.eat.foodservice.dto.response.FoodOptionResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import com.food.eat.foodservice.dto.response.PriceResponse;
import com.food.eat.foodservice.entity.Category;
import com.food.eat.foodservice.entity.Food;
import com.food.eat.foodservice.entity.FoodImage;
import com.food.eat.foodservice.entity.FoodOption;
import com.food.eat.foodservice.entity.FoodStatus;
import com.food.eat.foodservice.entity.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    @Mapping(target = "price", source = "price", qualifiedByName = "toPrice")
    @Mapping(target = "foodImage", source = "image", qualifiedByName = "toFoodImageList")
    @Mapping(target = "foodOption", source = "foodOption", qualifiedByName = "toFoodOptionList")
    @Mapping(target = "foodStatus", source = "foodStatus", qualifiedByName = "toFoodStatusList")
    @Mapping(target = "foodId", ignore = true)
    @Mapping(target = "category", ignore = true)
    Food toFood(FoodRequest foodRequest);

    @Mapping(target = "image", source = "foodImage")
    FoodResponse toFoodResponse(Food food);

    CategoryResponse toCategoryResponse(Category category);

    PriceResponse toPriceResponse(Price price);

    default String map(FoodImage foodImage) {
        return foodImage != null ? foodImage.getImage() : null;
    }

    @Named("toPrice")
    default Price toPrice(PriceRequest priceRequest) {
        if (priceRequest == null) return null;
        Price price = new Price();
        price.setOriginalPrice(priceRequest.originalPrice() != null ? String.valueOf(priceRequest.originalPrice()) : null);
        price.setDiscountPrice(priceRequest.discountPrice() != null ? String.valueOf(priceRequest.discountPrice()) : null);
        return price;
    }

    @Named("toFoodImageList")
    default List<FoodImage> toFoodImageList(List<String> images) {
        if (images == null) return null;
        return images.stream()
                .filter(img -> img != null && !img.isBlank())
                .map(img -> {
                    FoodImage fi = new FoodImage();
                    fi.setImage(img);
                    return fi;
                })
                .toList();
    }

    @Named("toFoodOptionList")
    default List<FoodOption> toFoodOptionList(List<FoodOptionRequest> requests) {
        if (requests == null) return null;
        return requests.stream().map(req -> {
            FoodOption option = new FoodOption();
            option.setName(req.name());
            option.setExtraPrice(req.extraPrice());
            return option;
        }).toList();
    }

    @Named("toFoodStatusList")
    default List<FoodStatus> toFoodStatusList(List<FoodStatusRequest> requests) {
        if (requests == null) return null;
        return requests.stream().map(req -> {
            FoodStatus status = new FoodStatus();
            status.setIsFeatured(req.isFeatured());
            status.setIsLiked(req.isLiked());
            return status;
        }).toList();
    }
}
