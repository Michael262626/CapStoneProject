package com.africa.semiclon.capStoneProject.security.repository;

import com.africa.semiclon.capStoneProject.security.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
}
