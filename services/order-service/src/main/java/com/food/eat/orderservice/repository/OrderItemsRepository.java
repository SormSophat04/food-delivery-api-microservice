package com.food.eat.orderservice.repository;

import com.food.eat.orderservice.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrderId(Long orderId);
    void deleteByOrderId(Long orderId);
}
