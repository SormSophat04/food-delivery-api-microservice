package com.food.eat.paymentservice.mapper;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;
import com.food.eat.paymentservice.entity.CreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    @Mapping(target = "creditCardId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CreditCard toEntity(CreditCardRequest creditCardRequest);

    CreditCardRequest toCreditCardRequest(CreditCard creditCard);

    CreditCardResponse toCreditCardResponse(CreditCard  creditCard);

}
