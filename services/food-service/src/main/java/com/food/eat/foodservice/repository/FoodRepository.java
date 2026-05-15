package com.food.eat.foodservice.repository;

import com.food.eat.foodservice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategoryCategoryId(Long categoryId);
    List<Food> findByAvailableTrue();
}
