package com.food.eat.foodservice.service.impl;

import com.food.eat.foodservice.dto.request.CategoryRequest;
import com.food.eat.foodservice.dto.response.CategoryResponse;
import com.food.eat.foodservice.entity.Category;
import com.food.eat.foodservice.repository.CategoryRepository;
import com.food.eat.foodservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.categoryName());
        category.setRestaurantId(request.restaurantId());
        category.setDisplayOrder(request.displayOrder());
        category.setImage(request.image());

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CategoryResponse> getCategoriesByRestaurant(Long restaurantId) {
        return categoryRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setCategoryName(request.categoryName());
        category.setRestaurantId(request.restaurantId());
        category.setDisplayOrder(request.displayOrder());
        category.setImage(request.image());

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public Page<CategoryResponse> getCategoriesByRestaurant(Long restaurantId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CategoryResponse> getCategories(Pageable pageable) {
        return null;
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(), category.getCategoryName(),
                category.getRestaurantId(), category.getDisplayOrder(), category.getImage());
    }
}
