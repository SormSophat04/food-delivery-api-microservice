package com.food.eat.paymentservice.client;

import com.food.eat.paymentservice.dto.request.BakongKhqrRequest;
import com.food.eat.paymentservice.dto.response.BakongKhqrResponse;
import com.food.eat.paymentservice.dto.response.BakongTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bakong-client", url = "${bakong.base-url}")
public interface BakongClient {

    @PostMapping("/khqr/generate")
    BakongKhqrResponse generateKhqr(
            @RequestBody
            BakongKhqrRequest request
    );

    @GetMapping("/transactions/{id}")
    BakongTransactionResponse checkTransaction(
            @PathVariable String id
    );
}
