package com.food.eat.foodservice.repository;

import com.food.eat.foodservice.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findFoodsByCategoryCategoryId(Long categoryId, Pageable pageable);
}
