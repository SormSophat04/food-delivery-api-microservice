package com.food.eat.orderservice.repository;

import com.food.eat.orderservice.entity.Order;
import com.food.eat.orderservice.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByPlacedAtDesc(Long userId);
//    List<Order> findByRestaurantId(Long restaurantId);
//    List<Order> findByOrderStatus(OrderStatus status);
}
