package com.food.eat.foodservice.service;

import com.food.eat.foodservice.dto.request.CategoryRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> getAllCategories();
    List<CategoryResponse> getCategoriesByRestaurant(Long restaurantId);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest request);
    void deleteCategory(Long categoryId);

    Page<CategoryResponse> getCategoriesByRestaurant(Long restaurantId, Pageable pageable);
    Page<CategoryResponse> getCategories(Pageable pageable);
}
