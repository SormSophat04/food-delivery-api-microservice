package com.food.eat.orderservice.service;

import com.food.eat.orderservice.dto.request.OrderRequest;
import com.food.eat.orderservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(Long userId, OrderRequest request);

    OrderResponse getOrderById(Long orderId, Long userId);

    List<OrderResponse> getUserOrders(Long userId);

    OrderResponse updateOrderStatus(Long orderId, String status);

    void cancelOrder(Long orderId, Long userId);
}
