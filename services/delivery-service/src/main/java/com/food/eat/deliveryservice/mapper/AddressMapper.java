package com.food.eat.deliveryservice.mapper;

import com.food.eat.deliveryservice.dto.request.AddressRequest;
import com.food.eat.deliveryservice.dto.response.AddressResponse;
import com.food.eat.deliveryservice.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(AddressRequest request, @MappingTarget Address address);
}
