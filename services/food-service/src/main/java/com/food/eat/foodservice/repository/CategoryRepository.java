package com.food.eat.foodservice.repository;

import com.food.eat.foodservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRestaurantId(Long restaurantId);

    Page<Category> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);
}
