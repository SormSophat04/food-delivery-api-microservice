package com.food.eat.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service")
public interface OrderClient {

    @PutMapping("/api/v1/orders/{orderId}/paid")
    void markOrderAsPaid(@PathVariable Long orderId);

}
