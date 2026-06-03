package com.food.eat.paymentservice.mapper;

import com.food.eat.paymentservice.dto.request.CreditCardRequest;
import com.food.eat.paymentservice.dto.response.CreditCardResponse;
import com.food.eat.paymentservice.entity.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardMapperTest {

    private CreditCardMapperImpl creditCardMapper;

    @BeforeEach
    void setUp() {
        creditCardMapper = new CreditCardMapperImpl();
    }

    @Test
    void toEntity_shouldMapFields() {
        CreditCardRequest request = new CreditCardRequest(1L, 123456, 12, 28, 123, "John Doe");

        CreditCard creditCard = creditCardMapper.toEntity(request);

        assertThat(creditCard).isNotNull();
        assertThat(creditCard.getUserId()).isEqualTo(1L);
        assertThat(creditCard.getCardNumber()).isEqualTo(123456);
        assertThat(creditCard.getExpiryMonth()).isEqualTo(12);
        assertThat(creditCard.getExpiryYear()).isEqualTo(28);
        assertThat(creditCard.getCvv()).isEqualTo(123);
        assertThat(creditCard.getCardHolderName()).isEqualTo("John Doe");
        assertThat(creditCard.getCreditCardId()).isNull();
    }

    @Test
    void toCreditCardRequest_shouldMapFields() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardId(1L);
        creditCard.setUserId(2L);
        creditCard.setCardNumber(550055);
        creditCard.setExpiryMonth(6);
        creditCard.setExpiryYear(30);
        creditCard.setCvv(456);
        creditCard.setCardHolderName("Jane Doe");

        CreditCardRequest request = creditCardMapper.toCreditCardRequest(creditCard);

        assertThat(request).isNotNull();
        assertThat(request.userId()).isEqualTo(2L);
        assertThat(request.cardNumber()).isEqualTo(550055);
        assertThat(request.expiryMonth()).isEqualTo(6);
        assertThat(request.expiryYear()).isEqualTo(30);
        assertThat(request.cvv()).isEqualTo(456);
        assertThat(request.cardHolderName()).isEqualTo("Jane Doe");
    }

    @Test
    void toCreditCardResponse_shouldMapFields() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardId(1L);
        creditCard.setUserId(3L);
        creditCard.setCardNumber(789789);
        creditCard.setExpiryMonth(9);
        creditCard.setExpiryYear(29);
        creditCard.setCvv(789);
        creditCard.setCardHolderName("Alice Smith");

        CreditCardResponse response = creditCardMapper.toCreditCardResponse(creditCard);

        assertThat(response).isNotNull();
        assertThat(response.creditCardId()).isEqualTo(1L);
        assertThat(response.userId()).isEqualTo(3L);
        assertThat(response.cardNumber()).isEqualTo(789789);
        assertThat(response.expiryMonth()).isEqualTo(9);
        assertThat(response.expiryYear()).isEqualTo(29);
        assertThat(response.cvv()).isEqualTo(789);
        assertThat(response.cardHolderName()).isEqualTo("Alice Smith");
    }
}
