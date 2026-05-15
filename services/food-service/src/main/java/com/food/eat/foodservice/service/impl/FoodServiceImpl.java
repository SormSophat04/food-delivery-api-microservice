package com.food.eat.foodservice.service.impl;

import com.food.eat.foodservice.dto.request.FoodRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;
import com.food.eat.foodservice.dto.response.FoodOptionResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import com.food.eat.foodservice.entity.Category;
import com.food.eat.foodservice.entity.Food;
import com.food.eat.foodservice.entity.FoodImage;
import com.food.eat.foodservice.repository.CategoryRepository;
import com.food.eat.foodservice.repository.FoodImageRepository;
import com.food.eat.foodservice.repository.FoodOptionRepository;
import com.food.eat.foodservice.repository.FoodRepository;
import com.food.eat.foodservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final FoodImageRepository foodImageRepository;
    private final FoodOptionRepository foodOptionRepository;

    @Override
    @Transactional
    public FoodResponse createFood(FoodRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        FoodImage foodImage = null;
        if (request.image() != null && !request.image().isBlank()) {
            foodImage = new FoodImage();
            foodImage.setImage(request.image());
            foodImage = foodImageRepository.save(foodImage);
        }

        Food food = new Food();
        food.setName(request.name());
        food.setPrice(request.price());
        food.setDescription(request.description());
        food.setCategory(category);
        food.setAvailable(request.available() != null ? request.available() : true);
        food.setFoodImage(foodImage);

        Food saved = foodRepository.save(food);
        return toFoodResponse(saved);
    }

    @Override
    public FoodResponse getFoodById(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));
        return toFoodResponse(food);
    }

    @Override
    public List<FoodResponse> getAllFoods() {
        return foodRepository.findByAvailableTrue().stream()
                .map(this::toFoodResponse)
                .toList();
    }

    @Override
    public List<FoodResponse> getFoodsByCategory(Long categoryId) {
        return foodRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::toFoodResponse)
                .toList();
    }

    @Override
    @Transactional
    public FoodResponse updateFood(Long foodId, FoodRequest request) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        food.setName(request.name());
        food.setPrice(request.price());
        food.setDescription(request.description());
        food.setCategory(category);
        food.setAvailable(request.available() != null ? request.available() : true);

        if (request.image() != null && !request.image().isBlank()) {
            if (food.getFoodImage() == null) {
                FoodImage foodImage = new FoodImage();
                foodImage.setImage(request.image());
                foodImage = foodImageRepository.save(foodImage);
                food.setFoodImage(foodImage);
            } else {
                food.getFoodImage().setImage(request.image());
            }
        }

        Food saved = foodRepository.save(food);
        return toFoodResponse(saved);
    }

    @Override
    @Transactional
    public void deleteFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));
        foodRepository.delete(food);
    }

    @Override
    public List<FoodOptionResponse> getFoodOptions(Long foodId) {
        return foodOptionRepository.findByFoodFoodId(foodId).stream()
                .map(option -> new FoodOptionResponse(
                        option.getFoodOptionId(),
                        option.getName(),
                        option.getExtraPrice()))
                .toList();
    }

    private FoodResponse toFoodResponse(Food food) {
        CategoryResponse categoryResponse = null;
        if (food.getCategory() != null) {
            Category cat = food.getCategory();
            categoryResponse = new CategoryResponse(
                    cat.getCategoryId(), cat.getCategoryName(),
                    cat.getRestaurantId(), cat.getDisplayOrder(), cat.getImage());
        }
        String image = food.getFoodImage() != null ? food.getFoodImage().getImage() : null;
        return new FoodResponse(
                food.getFoodId(), food.getName(), food.getPrice(),
                food.getDescription(), categoryResponse,
                food.getAvailable(), image);
    }
}
