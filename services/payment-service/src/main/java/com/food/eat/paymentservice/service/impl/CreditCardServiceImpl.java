package com.food.eat.paymentservice.service.impl;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;
import com.food.eat.paymentservice.entity.CreditCard;
import com.food.eat.paymentservice.mapper.CreditCardMapper;
import com.food.eat.paymentservice.repository.CreditCardRepository;
import com.food.eat.paymentservice.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;

    @Override
    public CreditCardResponse createCreditCard(CreditCardRequest creditCardRequest, Long userId) {
        CreditCard creditCard = creditCardMapper.toEntity(creditCardRequest);
        creditCard.setUserId(userId);
        CreditCard saved = creditCardRepository.save(creditCard);
        return creditCardMapper.toCreditCardResponse(saved);
    }

    @Override
    public CreditCardResponse getCreditCard(Long creditCardId) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found"));
        return creditCardMapper.toCreditCardResponse(creditCard);
    }

    @Override
    public List<CreditCardResponse> getCreditCards() {
        return creditCardRepository.findAll().stream()
                .map(creditCardMapper::toCreditCardResponse)
                .toList();
    }

    @Override
    public CreditCardResponse updateCreditCard(Long creditCardId, CreditCardRequest creditCardRequest) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found"));
        creditCard.setCardNumber(creditCardRequest.cardNumber());
        creditCard.setExpiryMonth(creditCardRequest.expiryMonth());
        creditCard.setExpiryYear(creditCardRequest.expiryYear());
        creditCard.setCvv(creditCardRequest.cvv());
        creditCard.setCardHolderName(creditCardRequest.cardHolderName());
        CreditCard saved = creditCardRepository.save(creditCard);
        return creditCardMapper.toCreditCardResponse(saved);
    }

    @Override
    public void deleteCreditCard(Long creditCardId) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found"));
        creditCardRepository.delete(creditCard);
    }
}
