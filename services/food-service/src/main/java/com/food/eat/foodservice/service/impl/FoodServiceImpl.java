package com.food.eat.foodservice.service.impl;

import com.food.eat.foodservice.dto.request.FoodRequest;
import com.food.eat.foodservice.dto.response.FoodOptionResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import com.food.eat.foodservice.entity.Category;
import com.food.eat.foodservice.entity.Food;
import com.food.eat.foodservice.entity.FoodImage;
import com.food.eat.foodservice.entity.Price;
import com.food.eat.foodservice.mapper.FoodMapper;
import com.food.eat.foodservice.repository.CategoryRepository;
import com.food.eat.foodservice.repository.FoodOptionRepository;
import com.food.eat.foodservice.repository.FoodRepository;
import com.food.eat.foodservice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public FoodResponse createFood(FoodRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Food food = foodMapper.toFood(request);
        food.setCategory(category);

        if (food.getFoodImage() != null) {
            food.getFoodImage().forEach(img -> img.setFood(food));
        }
        if (food.getFoodOption() != null) {
            food.getFoodOption().forEach(opt -> opt.setFood(food));
        }
        if (food.getFoodStatus() != null) {
            food.getFoodStatus().forEach(st -> st.setFood(food));
        }

        Food saved = foodRepository.save(food);
        return foodMapper.toFoodResponse(saved);
    }

    @Override
    public FoodResponse getFoodById(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found "+foodId));
        return foodMapper.toFoodResponse(food);
    }

    @Override
    @Transactional
    public FoodResponse updateFood(Long foodId, FoodRequest request) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        food.setName(request.name());
        food.setDescription(request.description());
        food.setCategory(category);
        food.setAvailable(request.available() != null ? request.available() : true);

        if (request.price() != null) {
            if (food.getPrice() == null) {
                food.setPrice(new Price());
            }
            food.getPrice().setOriginalPrice(request.price().originalPrice() != null ? String.valueOf(request.price().originalPrice()) : null);
            food.getPrice().setDiscountPrice(request.price().discountPrice() != null ? String.valueOf(request.price().discountPrice()) : null);
        }

/*
        if (request.image() != null && !request.image().clone()) {
            if (food.getFoodImage() == null) {
                FoodImage foodImage = new FoodImage();
                foodImage.setImage(request.image());
                food.setFoodImage(foodImage);
            } else {
                food.getFoodImage().setImage(request.image());
            }
        }
*/

        Food saved = foodRepository.save(food);
        return foodMapper.toFoodResponse(saved);
    }

    @Override
    @Transactional
    public void deleteFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));
        foodRepository.delete(food);
    }

    @Override
    public Page<FoodResponse> getFoods(int page, int size) {
        return foodRepository.findAll(PageRequest.of(page, size)).map(foodMapper::toFoodResponse);
    }
}
