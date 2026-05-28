package com.food.eat.authservice.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtUtil {

    private static final int MIN_SECRET_BYTES = 32;

    private final JWSSigner signer;
    private final String keyId = UUID.randomUUID().toString();

    @Value("${security.jwt.expiration-ms}")
    private Long expirationMs;

    @Value("${security.jwt.issuer}")
    private String issuer;

    public JwtUtil(@Value("${security.jwt.secret}") String secret) {
        byte[] normalizedSecretBytes = normalizeSecret(secret).getBytes(StandardCharsets.UTF_8);
        try {
            this.signer = new MACSigner(normalizedSecretBytes);
        } catch (JOSEException e) {
            throw new IllegalStateException("Failed to initialize JWT signer", e);
        }
    }

    public String generateAccessToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        List<String> authoritiesStr = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(expirationMs);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subject)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiresAt))
                .jwtID(UUID.randomUUID().toString())
                .claim("authorities", authoritiesStr)
                .claim("scope", String.join(" ", authoritiesStr))
                .build();

        return signToken(claims);
    }

    public String generateRefreshToken(String subject) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(86400);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subject)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiresAt))
                .jwtID(UUID.randomUUID().toString())
                .claim("type", "refresh")
                .build();

        return signToken(claims);
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    private String signToken(JWTClaimsSet claims) {
        try {
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .keyID(keyId)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new IllegalStateException("Failed to sign token", e);
        }
    }

    private String normalizeSecret(String secret) {
        String normalized = secret;
        while (normalized.getBytes(StandardCharsets.UTF_8).length < MIN_SECRET_BYTES) {
            normalized += "0";
        }
        return normalized;
    }
}
