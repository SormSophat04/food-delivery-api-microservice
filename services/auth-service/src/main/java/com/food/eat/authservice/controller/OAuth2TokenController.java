package com.food.eat.authservice.controller;

import com.food.eat.authservice.dto.response.ApiMessageResponse;
import com.food.eat.authservice.dto.response.AuthResponse;
import com.food.eat.authservice.entity.RefreshToken;
import com.food.eat.authservice.entity.User;
import com.food.eat.authservice.jwt.JwtUtil;
import com.food.eat.authservice.repository.RefreshTokenRepository;
import com.food.eat.authservice.repository.UserRepository;
import com.food.eat.authservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OAuth2TokenController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;

    @PostMapping(value = "/api/oauth2/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> token(
            @RequestParam("grant_type") String grantType,
            @RequestParam Map<String, String> params) {

        return switch (grantType) {
            case "password" -> handlePasswordGrant(params);
            case "refresh_token" -> handleRefreshTokenGrant(params);
            case "client_credentials" -> handleClientCredentialsGrant(params);
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiMessageResponse("Unsupported grant_type: " + grantType));
        };
    }

    private ResponseEntity<?> handlePasswordGrant(Map<String, String> params) {
        String email = params.get("username");
        String password = params.get("password");

        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiMessageResponse("username and password are required"));
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        if (!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is not verified");
        }

        AuthResponse authResponse = userService.generateOAuth2Tokens(user);
        return ResponseEntity.ok(authResponse);
    }

    private ResponseEntity<?> handleRefreshTokenGrant(Map<String, String> params) {
        String refreshTokenValue = params.get("refresh_token");

        if (refreshTokenValue == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiMessageResponse("refresh_token is required"));
        }

        RefreshToken storedToken = refreshTokenRepository.findByTokenAndRevokedFalse(refreshTokenValue)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or revoked refresh token"));

        if (storedToken.getExpiresAt().isBefore(Instant.now())) {
            storedToken.setRevoked(true);
            refreshTokenRepository.save(storedToken);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token has expired");
        }

        User user = userRepository.findByEmail(storedToken.getUserEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        AuthResponse authResponse = userService.generateOAuth2Tokens(user);
        return ResponseEntity.ok(authResponse);
    }

    private ResponseEntity<?> handleClientCredentialsGrant(Map<String, String> params) {
        String accessToken = jwtUtil.generateAccessToken("system", List.of(new SimpleGrantedAuthority("ROLE_SYSTEM")));

        return ResponseEntity.ok(Map.of(
                "access_token", accessToken,
                "token_type", "Bearer",
                "expires_in", jwtUtil.getExpirationMs() / 1000,
                "scope", "roles"
        ));
    }
}
