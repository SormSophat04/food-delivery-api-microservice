package com.food.eat.authservice.service.impl;

import com.food.eat.authservice.dto.request.LoginRequest;
import com.food.eat.authservice.dto.request.RegisterRequest;
import com.food.eat.authservice.dto.request.VerifyCodeRequest;
import com.food.eat.authservice.dto.event.UserEvent;
import com.food.eat.authservice.dto.response.ApiMessageResponse;
import com.food.eat.authservice.dto.response.AuthResponse;
import com.food.eat.authservice.dto.response.UserResponse;
import com.food.eat.authservice.entity.EmailVerificationCode;
import com.food.eat.authservice.entity.RefreshToken;
import com.food.eat.authservice.enums.OtpPurpose;
import com.food.eat.authservice.entity.User;
import com.food.eat.authservice.jwt.JwtUtil;
import com.food.eat.authservice.repository.EmailVerificationCodeRepository;
import com.food.eat.authservice.repository.RefreshTokenRepository;
import com.food.eat.authservice.repository.UserRepository;
import com.food.eat.authservice.security.AuthUser;
import com.food.eat.authservice.service.EmailService;
import com.food.eat.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final UserRepository userRepository;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.otp.length:6}")
    private int otpLength;

    @Value("${app.otp.ttl-minutes:10}")
    private long otpTtlMinutes;

    @Override
    public UserDetails getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return toAuthUser(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return toUserResponse(user);
    }

    @Override
    @Transactional
    public ApiMessageResponse register(RegisterRequest registerRequest) {
        User user = userRepository.findByEmail(registerRequest.email()).orElseGet(User::new);

        if (user.getUserId() != null && user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPhoneNumber(registerRequest.phoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole(DEFAULT_ROLE);
        user.setEnabled(false);

        userRepository.save(user);
        sendCode(registerRequest.email(), OtpPurpose.REGISTER);

        return new ApiMessageResponse("Registration code sent to email");
    }

    @Override
    @Transactional
    public AuthResponse verifyRegisterCode(VerifyCodeRequest verifyCodeRequest) {
        User user = userRepository.findByEmail(verifyCodeRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.isEnabled()) {
            return generateAuthTokens(user);
        }

        EmailVerificationCode code = getActiveCode(verifyCodeRequest.email(), OtpPurpose.REGISTER);
        validateCode(code, verifyCodeRequest.code());

        code.setVerified(true);
        user.setEnabled(true);

        emailVerificationCodeRepository.save(code);
        userRepository.save(user);

        if (kafkaTemplate != null) {
            kafkaTemplate.send("user-topic", new UserEvent(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getImage(),
                    user.getRole(),
                    user.isEnabled()
            ));
        }

        return generateAuthTokens(user);
    }

    @Override
    @Transactional
    public ApiMessageResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        if (!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is not verified");
        }

        sendCode(loginRequest.email(), OtpPurpose.LOGIN);
        return new ApiMessageResponse("Login code sent to email");
    }

    @Override
    @Transactional
    public AuthResponse verifyLoginCode(VerifyCodeRequest verifyCodeRequest) {
        User user = userRepository.findByEmail(verifyCodeRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid verification request"));

        if (!user.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is not verified");
        }

        EmailVerificationCode code = getActiveCode(verifyCodeRequest.email(), OtpPurpose.LOGIN);
        validateCode(code, verifyCodeRequest.code());
        code.setVerified(true);
        emailVerificationCodeRepository.save(code);

        return generateAuthTokens(user);
    }

    public AuthResponse generateAuthTokens(User user) {
        AuthUser authUser = toAuthUser(user);
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), authUser.getAuthorities());

        String refreshTokenValue = jwtUtil.generateRefreshToken(user.getEmail());
        saveRefreshToken(refreshTokenValue, user.getEmail());

        return new AuthResponse(
                "Bearer",
                accessToken,
                jwtUtil.getExpirationMs(),
                refreshTokenValue,
                "roles",
                toUserResponse(user)
        );
    }

    private void saveRefreshToken(String token, String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUserEmail(userEmail);
        refreshToken.setExpiresAt(Instant.now().plusSeconds(86400));
        refreshTokenRepository.save(refreshToken);
    }

    private void sendCode(String email, OtpPurpose purpose) {
        markAllCodesAsUsed(email, purpose);

        String rawCode = generateCode();
        EmailVerificationCode code = new EmailVerificationCode();
        code.setEmail(email);
        code.setPurpose(purpose);
        code.setCodeHash(passwordEncoder.encode(rawCode));
        code.setExpiresAt(LocalDateTime.now().plusMinutes(otpTtlMinutes));

        emailVerificationCodeRepository.save(code);
        emailService.sendOtpCode(email, rawCode, purpose, otpTtlMinutes);
    }

    private void markAllCodesAsUsed(String email, OtpPurpose purpose) {
        List<EmailVerificationCode> activeCodes =
                emailVerificationCodeRepository.findByEmailAndPurposeAndVerifiedFalse(email, purpose);

        if (activeCodes.isEmpty()) {
            return;
        }

        activeCodes.forEach(existingCode -> existingCode.setVerified(true));
        emailVerificationCodeRepository.saveAll(activeCodes);
    }

    private EmailVerificationCode getActiveCode(String email, OtpPurpose purpose) {
        return emailVerificationCodeRepository.findTopByEmailAndPurposeAndVerifiedFalseOrderByCreatedAtDesc(email, purpose)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No active verification code"));
    }

    private void validateCode(EmailVerificationCode code, String rawCode) {
        if (code.getExpiresAt().isBefore(LocalDateTime.now())) {
            code.setVerified(true);
            emailVerificationCodeRepository.save(code);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification code has expired");
        }

        if (!passwordEncoder.matches(rawCode, code.getCodeHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid verification code");
        }
    }

    private String generateCode() {
        int length = Math.max(4, Math.min(8, otpLength));
        int upperBound = (int) Math.pow(10, length);
        int value = secureRandom.nextInt(upperBound);
        String format = "%0" + length + "d";
        return String.format(format, value);
    }

    private AuthUser toAuthUser(User user) {
        return AuthUser.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .enabled(user.isEnabled())
                .build();
    }

    private UserResponse toUserResponse(User user) {
        if (kafkaTemplate != null) {
            kafkaTemplate.send("user-topic", new UserEvent(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getImage(),
                    user.getRole(),
                    user.isEnabled()
            ));
        }
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getImage(),
                user.getRole(),
                user.isEnabled()
        );
    }
}
