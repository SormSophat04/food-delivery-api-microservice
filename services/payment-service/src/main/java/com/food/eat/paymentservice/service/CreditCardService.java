package com.food.eat.paymentservice.service;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;

import java.util.List;

public interface CreditCardService {

    CreditCardResponse createCreditCard(CreditCardRequest creditCardRequest, Long userId);
    CreditCardResponse getCreditCard(Long creditCardId);
    List<CreditCardResponse> getCreditCards();
    CreditCardResponse updateCreditCard(CreditCardRequest creditCardRequest);
    void deleteCreditCard(Long creditCardId);
}
