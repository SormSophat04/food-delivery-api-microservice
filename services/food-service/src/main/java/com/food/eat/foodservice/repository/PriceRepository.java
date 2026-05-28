package com.food.eat.foodservice.repository;

import com.food.eat.foodservice.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
