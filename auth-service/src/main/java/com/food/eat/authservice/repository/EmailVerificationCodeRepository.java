package com.food.eat.authservice.repository;

import com.food.eat.authservice.entity.EmailVerificationCode;
import com.food.eat.authservice.enums.OtpPurpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Long> {

    List<EmailVerificationCode> findByEmailAndPurposeAndVerifiedFalse(String email, OtpPurpose purpose);

    Optional<EmailVerificationCode> findTopByEmailAndPurposeAndVerifiedFalseOrderByCreatedAtDesc(String email, OtpPurpose purpose);
}
