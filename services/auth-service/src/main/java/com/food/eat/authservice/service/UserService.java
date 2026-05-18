package com.food.eat.authservice.service;

import com.food.eat.authservice.dto.request.LoginRequest;
import com.food.eat.authservice.dto.request.RegisterRequest;
import com.food.eat.authservice.dto.request.VerifyCodeRequest;
import com.food.eat.authservice.dto.response.ApiMessageResponse;
import com.food.eat.authservice.dto.response.AuthResponse;
import com.food.eat.authservice.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails getUserByEmail(String email);

    UserResponse getUserById(Long id);

    ApiMessageResponse register(RegisterRequest registerRequest);

    AuthResponse verifyRegisterCode(VerifyCodeRequest verifyCodeRequest);

    ApiMessageResponse login(LoginRequest loginRequest);

    AuthResponse verifyLoginCode(VerifyCodeRequest verifyCodeRequest);
}
