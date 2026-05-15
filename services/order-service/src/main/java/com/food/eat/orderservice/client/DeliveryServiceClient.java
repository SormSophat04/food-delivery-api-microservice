package com.food.eat.orderservice.client;

import com.food.eat.orderservice.dto.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-service", path = "/api/addresses")
public interface DeliveryServiceClient {

    @GetMapping("/{addressId}")
    AddressResponse getAddress(@PathVariable String addressId, @RequestParam Long userId);
}
