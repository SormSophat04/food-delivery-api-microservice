package com.food.eat.authservice.controller;

import com.food.eat.authservice.dto.request.LoginRequest;
import com.food.eat.authservice.dto.request.RegisterRequest;
import com.food.eat.authservice.dto.request.VerifyCodeRequest;
import com.food.eat.authservice.dto.response.ApiMessageResponse;
import com.food.eat.authservice.dto.response.AuthResponse;
import com.food.eat.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiMessageResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/register/verify-code")
    public ResponseEntity<AuthResponse> verifyRegisterCode(@Valid @RequestBody VerifyCodeRequest request) {
        return ResponseEntity.ok(userService.verifyRegisterCode(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiMessageResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/login/verify-code")
    public ResponseEntity<AuthResponse> verifyLoginCode(@Valid @RequestBody VerifyCodeRequest request) {
        return ResponseEntity.ok(userService.verifyLoginCode(request));
    }
}
