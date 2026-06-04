package com.ess.essreddit.services;

import com.ess.essreddit.model.RefreshToken;
import com.ess.essreddit.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    boolean validateToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByRefreshToken(token);

        return refreshToken.isPresent();
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByRefreshToken(token);
    }
}
