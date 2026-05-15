package com.food.eat.authservice.service;

import com.food.eat.authservice.enums.OtpPurpose;

public interface EmailService {

    void sendOtpCode(String email, String code, OtpPurpose purpose, long ttlMinutes);
}
