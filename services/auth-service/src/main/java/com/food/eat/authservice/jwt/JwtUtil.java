package com.food.eat.authservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms}")
    private Long expirationMs;

    @Value("${security.jwt.issuer}")
    private String issuer;

    public String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        List<String> authoritiesStr = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expirationDate)
                .claim("authorities", authoritiesStr)
                .signWith(signingKey())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    private SecretKey signingKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            String paddedSecret = secret;
            while (paddedSecret.getBytes(StandardCharsets.UTF_8).length < 32) {
                paddedSecret += "0";
            }
            return Keys.hmacShaKeyFor(paddedSecret.getBytes(StandardCharsets.UTF_8));
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
