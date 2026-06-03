package com.food.eat.paymentservice.mapper;

import com.food.eat.paymentservice.dto.request.PaymentRequest;
import com.food.eat.paymentservice.dto.response.PaymentResponse;
import com.food.eat.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "qrContent", ignore = true)
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "paidAt", ignore = true)
    @Mapping(target = "expiredAt", ignore = true)
    Payment toEntity(PaymentRequest paymentRequest);

    PaymentResponse toResponse(Payment payment);

    PaymentRequest toRequest(Payment payment);
}
