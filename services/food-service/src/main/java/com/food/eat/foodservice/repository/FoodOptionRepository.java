package com.food.eat.foodservice.repository;

import com.food.eat.foodservice.entity.FoodOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOptionRepository extends JpaRepository<FoodOption, Long> {
    List<FoodOption> findByFoodFoodId(Long foodId);
}
