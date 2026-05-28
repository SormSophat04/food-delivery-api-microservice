package com.food.eat.authservice.repository;

import com.food.eat.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByUserEmail(String userEmail);
}
