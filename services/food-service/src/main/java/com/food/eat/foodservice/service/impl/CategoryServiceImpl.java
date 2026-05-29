package com.food.eat.foodservice.service.impl;

import com.food.eat.foodservice.dto.request.CategoryRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;
import com.food.eat.foodservice.dto.response.FoodResponse;
import com.food.eat.foodservice.entity.Category;
import com.food.eat.foodservice.mapper.CategoryMapper;
import com.food.eat.foodservice.mapper.FoodMapper;
import com.food.eat.foodservice.repository.CategoryRepository;
import com.food.eat.foodservice.repository.FoodRepository;
import com.food.eat.foodservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;
    private final CategoryMapper categoryMapper;
    private final FoodMapper foodMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return categoryMapper.toResponse(category);
    }

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
    }

    @Override
    public Page<CategoryResponse> searchCategoriesByName(String name, Pageable pageable) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(name, pageable)
                .map(categoryMapper::toResponse);
    }

    @Override
    public Stream<CategoryResponse> getCategoriesByRestaurant(Long restaurantId, Pageable pageable ) {
        return categoryRepository.findByRestaurantId(restaurantId).stream()
                .map(categoryMapper::toResponse);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryMapper.updateEntity(request, category);

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public Page<FoodResponse> getFoodsByCategory(Long categoryId, int page, int size) {
        getCategoryById(categoryId);
        return foodRepository.findFoodsByCategoryCategoryId(categoryId, PageRequest.of(page, size))
                .map(foodMapper::toFoodResponse);
    }
}
