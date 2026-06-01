package com.food.eat.orderservice.service.impl;

import com.food.eat.orderservice.client.AuthServiceClient;
import com.food.eat.orderservice.client.DeliveryServiceClient;
import com.food.eat.orderservice.client.FoodServiceClient;
import com.food.eat.orderservice.client.RestaurantServiceClient;
import com.food.eat.orderservice.dto.event.OrderEvent;
import com.food.eat.orderservice.dto.request.OrderItemRequest;
import com.food.eat.orderservice.dto.request.OrderRequest;
import com.food.eat.orderservice.dto.response.OrderItemResponse;
import com.food.eat.orderservice.dto.response.OrderResponse;
import com.food.eat.orderservice.entity.Order;
import com.food.eat.orderservice.entity.OrderItems;
import com.food.eat.orderservice.repository.OrderItemsRepository;
import com.food.eat.orderservice.enums.OrderStatus;
import com.food.eat.orderservice.repository.OrderRepository;
import com.food.eat.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final AuthServiceClient authServiceClient;
    private final FoodServiceClient foodServiceClient;
    private final DeliveryServiceClient deliveryServiceClient;
    private final RestaurantServiceClient restaurantServiceClient;

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {
        authServiceClient.getUserById(userId);
        restaurantServiceClient.getRestaurantById(request.restaurantId());
        deliveryServiceClient.getAddress(String.valueOf(request.deliveryAddress()), userId);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (OrderItemRequest item : request.items()) {
            var food = foodServiceClient.getFoodById(item.foodId());
            if (food.available() != null && !food.available()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Food item not available: " + food.name());
            }
            subtotal = subtotal.add(item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())));
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setRestaurantId(request.restaurantId());
        order.setDeliveryAddress(request.deliveryAddress());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setSubtotal(subtotal);
        order.setDeliveryFee(BigDecimal.valueOf(2.0));
        order.setTotal(subtotal.add(order.getDeliveryFee()));
        order.setNotes(request.notes());
        order.setPlacedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        for (OrderItemRequest item : request.items()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setFoodId(item.foodId());
            orderItem.setQuantity(item.quantity());
            orderItem.setUnitPrice(item.unitPrice());
            orderItem.setCustomisation(item.customisation());
            orderItemsRepository.save(orderItem);
        }

        publishOrderEvent(savedOrder);

        return toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order does not belong to this user");
        }
        return toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        authServiceClient.getUserById(userId);
        return orderRepository.findByUserIdOrderByPlacedAtDesc(userId).stream()
                .map(this::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
        if (OrderStatus.DELIVERED == order.getOrderStatus()) {
            order.setDeliveredAt(LocalDateTime.now());
        }
        Order saved = orderRepository.save(order);
        publishOrderEvent(saved);
        return toOrderResponse(saved);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order does not belong to this user");
        }
        if (OrderStatus.PENDING != order.getOrderStatus()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending orders can be cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        publishOrderEvent(order);
    }

    private void publishOrderEvent(Order order) {
        if (kafkaTemplate != null) {
            try {
                OrderEvent event = new OrderEvent(
                        order.getOrderId(), order.getUserId(),
                        order.getRestaurantId(), order.getOrderStatus().name(), order.getTotal());
                kafkaTemplate.send("order-topic", event);
            } catch (Exception e) {
                log.warn("Failed to publish order event: {}", e.getMessage());
            }
        }
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> items = orderItemsRepository.findByOrderId(order.getOrderId()).stream()
                .map(item -> new OrderItemResponse(
                        item.getOrderItemsId(), item.getFoodId(),
                        item.getQuantity(), item.getUnitPrice(), item.getCustomisation()))
                .toList();

        return new OrderResponse(
                order.getOrderId(), order.getUserId(), order.getRestaurantId(),
                order.getDeliveryAddress(), order.getOrderStatus().name(), order.getSubtotal(),
                order.getDeliveryFee(), order.getTotal(), order.getNotes(),
                order.getPlacedAt(), items);
    }
}
