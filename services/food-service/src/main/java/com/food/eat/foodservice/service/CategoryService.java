package com.food.eat.foodservice.service;

import com.food.eat.foodservice.dto.request.CategoryRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long categoryId);

    Page<CategoryResponse> getAllCategories(Pageable pageable);

    Page<CategoryResponse> searchCategoriesByName(String name, Pageable pageable);

    Stream<CategoryResponse> getCategoriesByRestaurant(Long restaurantId, Pageable pageable);

    CategoryResponse updateCategory(Long categoryId, CategoryRequest request);

    void deleteCategory(Long categoryId);

    Page<FoodResponse> getFoodsByCategory(Long categoryId, int page, int size);
}
