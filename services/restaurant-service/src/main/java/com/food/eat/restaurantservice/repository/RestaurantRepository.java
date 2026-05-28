package com.food.eat.restaurantservice.repository;

import com.food.eat.restaurantservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByOwnerId(Long ownerId);
    List<Restaurant> findByStatus(String status);
}
