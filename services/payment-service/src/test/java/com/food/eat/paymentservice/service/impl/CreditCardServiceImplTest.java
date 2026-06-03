package com.food.eat.paymentservice.service.impl;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;
import com.food.eat.paymentservice.entity.CreditCard;
import com.food.eat.paymentservice.mapper.CreditCardMapper;
import com.food.eat.paymentservice.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private CreditCardMapper creditCardMapper;

    private CreditCardServiceImpl creditCardService;

    @BeforeEach
    void setUp() {
        creditCardService = new CreditCardServiceImpl(creditCardRepository, creditCardMapper);
    }

    @Test
    void createCreditCard_shouldSaveAndReturn() {
        CreditCardRequest request = new CreditCardRequest(1L, 1234, 12, 28, 123, "John");
        CreditCard mapped = new CreditCard();
        CreditCard saved = new CreditCard();
        saved.setCreditCardId(1L);
        CreditCardResponse expected = new CreditCardResponse(1L, 1L, 1234, 12, 28, 123, "John");

        when(creditCardMapper.toEntity(request)).thenReturn(mapped);
        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(saved);
        when(creditCardMapper.toCreditCardResponse(saved)).thenReturn(expected);

        CreditCardResponse response = creditCardService.createCreditCard(request, 1L);

        assertThat(response).isSameAs(expected);
        verify(creditCardRepository).save(mapped);
    }

    @Test
    void getCreditCard_whenExists_shouldReturn() {
        CreditCard card = new CreditCard();
        card.setCreditCardId(1L);
        CreditCardResponse expected = new CreditCardResponse(1L, 1L, 1234, 12, 28, 123, "John");

        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(creditCardMapper.toCreditCardResponse(card)).thenReturn(expected);

        CreditCardResponse response = creditCardService.getCreditCard(1L);

        assertThat(response).isSameAs(expected);
    }

    @Test
    void getCreditCard_whenNotExists_shouldThrow() {
        when(creditCardRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creditCardService.getCreditCard(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Credit card not found");
    }

    @Test
    void getCreditCards_shouldReturnList() {
        CreditCard card = new CreditCard();
        CreditCardResponse response = new CreditCardResponse(1L, 1L, 1234, 12, 28, 123, "John");

        when(creditCardRepository.findAll()).thenReturn(List.of(card));
        when(creditCardMapper.toCreditCardResponse(card)).thenReturn(response);

        List<CreditCardResponse> cards = creditCardService.getCreditCards();

        assertThat(cards).containsExactly(response);
    }

    @Test
    void updateCreditCard_shouldUpdateAndReturn() {
        CreditCardRequest request = new CreditCardRequest(1L, 5500, 6, 30, 456, "Jane");
        CreditCard existing = new CreditCard();
        existing.setCreditCardId(1L);
        CreditCard saved = new CreditCard();
        CreditCardResponse expected = new CreditCardResponse(1L, 1L, 5500, 6, 30, 456, "Jane");

        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(creditCardRepository.save(existing)).thenReturn(saved);
        when(creditCardMapper.toCreditCardResponse(saved)).thenReturn(expected);

        CreditCardResponse response = creditCardService.updateCreditCard(1L, request);

        assertThat(response).isSameAs(expected);
        assertThat(existing.getCardNumber()).isEqualTo(5500);
        assertThat(existing.getExpiryMonth()).isEqualTo(6);
        assertThat(existing.getExpiryYear()).isEqualTo(30);
        assertThat(existing.getCvv()).isEqualTo(456);
        assertThat(existing.getCardHolderName()).isEqualTo("Jane");
    }

    @Test
    void deleteCreditCard_whenExists_shouldDelete() {
        CreditCard card = new CreditCard();
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(card));

        creditCardService.deleteCreditCard(1L);

        verify(creditCardRepository).delete(card);
    }

    @Test
    void deleteCreditCard_whenNotExists_shouldThrow() {
        when(creditCardRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creditCardService.deleteCreditCard(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Credit card not found");
    }
}
