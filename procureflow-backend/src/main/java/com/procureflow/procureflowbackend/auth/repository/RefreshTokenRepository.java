package com.procureflow.procureflowbackend.auth.repository;

import com.procureflow.procureflowbackend.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByTokenAndIsRevokedFalse(String token);



    boolean existsByTokenAndIsRevokedFalse(String token);

    long deleteByExpiresAtBefore(LocalDateTime dateTime);
}
