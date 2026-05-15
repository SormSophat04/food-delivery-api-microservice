package com.food.eat.foodservice.client;

import com.food.eat.foodservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", path = "/api/users")
public interface AuthServiceClient {

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") Long userId);
}
