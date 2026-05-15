package com.food.eat.deliveryservice.service.impl;

import com.food.eat.deliveryservice.dto.request.AddressRequest;
import com.food.eat.deliveryservice.dto.response.AddressResponse;
import com.food.eat.deliveryservice.entity.Address;
import com.food.eat.deliveryservice.mapper.AddressMapper;
import com.food.eat.deliveryservice.repository.AddressRepository;
import com.food.eat.deliveryservice.repository.UserCacheRepository;
import com.food.eat.deliveryservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserCacheRepository userCacheRepository;

    @Override
    public AddressResponse createAddress(Long userId, AddressRequest request) {
        validateUserExists(userId);

        Address address = addressMapper.toEntity(request);
        address.setUserId(userId);
        Address saved = addressRepository.save(address);
        return addressMapper.toResponse(saved);
    }

    @Override
    public List<AddressResponse> getAddresses(Long userId) {
        validateUserExists(userId);

        return addressRepository.findByUserId(userId).stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    public AddressResponse getAddress(Long userId, String addressId) {
        validateUserExists(userId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if (!address.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Address does not belong to this user");
        }

        return addressMapper.toResponse(address);
    }

    @Override
    public AddressResponse updateAddress(Long userId, String addressId, AddressRequest request) {
        validateUserExists(userId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if (!address.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Address does not belong to this user");
        }

        addressMapper.updateEntity(request, address);
        Address saved = addressRepository.save(address);
        return addressMapper.toResponse(saved);
    }

    @Override
    public void deleteAddress(Long userId, String addressId) {
        validateUserExists(userId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if (!address.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Address does not belong to this user");
        }

        addressRepository.delete(address);
    }

    private void validateUserExists(Long userId) {
        if (!userCacheRepository.existsByUserId(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
    }
}
