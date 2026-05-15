package com.food.eat.deliveryservice.controller;

import com.food.eat.deliveryservice.dto.request.AddressRequest;
import com.food.eat.deliveryservice.dto.response.AddressResponse;
import com.food.eat.deliveryservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @RequestParam Long userId,
            @Valid @RequestBody AddressRequest request
    ) {
        AddressResponse response = addressService.createAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses(
            @RequestParam Long userId
    ) {
        List<AddressResponse> responses = addressService.getAddresses(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(
            @RequestParam Long userId,
            @PathVariable String addressId
    ) {
        AddressResponse response = addressService.getAddress(userId, addressId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @RequestParam Long userId,
            @PathVariable String addressId,
            @Valid @RequestBody AddressRequest request
    ) {
        AddressResponse response = addressService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @RequestParam Long userId,
            @PathVariable String addressId
    ) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
