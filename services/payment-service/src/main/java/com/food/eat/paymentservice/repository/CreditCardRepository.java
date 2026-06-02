package com.food.eat.paymentservice.repository;

import com.food.eat.paymentservice.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
