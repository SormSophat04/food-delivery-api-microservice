package com.food.eat.restaurantservice.repository;

import com.food.eat.restaurantservice.enitity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByRestaurantId(Long restaurantId);
}
