package com.food.eat.paymentservice.service.impl;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;
import com.food.eat.paymentservice.mapper.CreditCardMapper;
import com.food.eat.paymentservice.repository.CreditCardRepository;
import com.food.eat.paymentservice.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;


    @Override
    public CreditCardResponse createCreditCard(CreditCardRequest creditCardRequest, Long userId) {

        return null;
    }

    @Override
    public CreditCardResponse getCreditCard(Long creditCardId) {
        return null;
    }

    @Override
    public List<CreditCardResponse> getCreditCards() {
        return List.of();
    }

    @Override
    public CreditCardResponse updateCreditCard(CreditCardRequest creditCardRequest) {
        return null;
    }

    @Override
    public void deleteCreditCard(Long creditCardId) {

    }
}
