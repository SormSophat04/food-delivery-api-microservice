package com.food.eat.paymentservice.controller;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;
import com.food.eat.paymentservice.service.CreditCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit-cards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<CreditCardResponse> createCreditCard(
            @RequestParam Long userId,
            @Valid @RequestBody CreditCardRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditCardService.createCreditCard(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<CreditCardResponse>> getCreditCards() {
        return ResponseEntity.ok(creditCardService.getCreditCards());
    }

    @GetMapping("/{creditCardId}")
    public ResponseEntity<CreditCardResponse> getCreditCard(@PathVariable Long creditCardId) {
        return ResponseEntity.ok(creditCardService.getCreditCard(creditCardId));
    }

    @PutMapping("/{creditCardId}")
    public ResponseEntity<CreditCardResponse> updateCreditCard(
            @PathVariable Long creditCardId,
            @Valid @RequestBody CreditCardRequest request
    ) {
        return ResponseEntity.ok(creditCardService.updateCreditCard(creditCardId, request));
    }

    @DeleteMapping("/{creditCardId}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long creditCardId) {
        creditCardService.deleteCreditCard(creditCardId);
        return ResponseEntity.noContent().build();
    }
}
