package com.food.eat.deliveryservice.service;

import com.food.eat.deliveryservice.dto.request.AddressRequest;
import com.food.eat.deliveryservice.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(Long userId, AddressRequest request);

    List<AddressResponse> getAddresses(Long userId);

    AddressResponse getAddress(Long userId, String addressId);

    AddressResponse updateAddress(Long userId, String addressId, AddressRequest request);

    void deleteAddress(Long userId, String addressId);
}
