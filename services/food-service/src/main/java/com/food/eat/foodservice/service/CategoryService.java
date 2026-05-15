package com.food.eat.foodservice.service;

import com.food.eat.foodservice.dto.request.CategoryRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> getAllCategories();
    List<CategoryResponse> getCategoriesByRestaurant(Long restaurantId);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest request);
    void deleteCategory(Long categoryId);
}
