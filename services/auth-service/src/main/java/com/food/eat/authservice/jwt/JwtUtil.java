package com.food.eat.authservice.jwt;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class JwtUtil {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${security.jwt.expiration-ms}")
    private Long expirationMs;

    @Value("${security.jwt.issuer}")
    private String issuer;

    public JwtUtil(JWKSource<SecurityContext> jwkSource) {
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);
        this.jwtDecoder = NimbusJwtDecoder.withJwkSource(jwkSource).build();
    }

    public String generateAccessToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        List<String> authoritiesStr = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(expirationMs);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(subject)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString())
                .claim("authorities", authoritiesStr)
                .claim("scope", String.join(" ", authoritiesStr))
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                claims
        );

        return jwtEncoder.encode(parameters).getTokenValue();
    }

    public String generateRefreshToken(String subject) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(86400);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(subject)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString())
                .claim("type", "refresh")
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                claims
        );

        return jwtEncoder.encode(parameters).getTokenValue();
    }

    public Jwt parseToken(String token) {
        return jwtDecoder.decode(token);
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public JwtEncoder getJwtEncoder() {
        return jwtEncoder;
    }
}
