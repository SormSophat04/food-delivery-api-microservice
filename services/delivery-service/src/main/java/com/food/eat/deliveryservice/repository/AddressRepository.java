package com.food.eat.deliveryservice.repository;

import com.food.eat.deliveryservice.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByUserId(Long userId);
}
