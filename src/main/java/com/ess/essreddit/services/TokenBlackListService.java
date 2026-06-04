package com.ess.essreddit.services;

import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.model.TokenBlackList;
import com.ess.essreddit.model.VerificationToken;
import com.ess.essreddit.repository.TokenBlackListRepository;
import com.ess.essreddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class TokenBlackListService {
    private final TokenBlackListRepository tokenBlackListRepository;

    public void blackListToken(String token) {
        if (!tokenBlackListRepository.existsByToken(token)) {
            TokenBlackList tokenBlackList = new TokenBlackList();
            tokenBlackList.setToken(token);
            tokenBlackListRepository.save(tokenBlackList);
            log.info("Token is blacklisted successfully!");
        } else {
            log.warn("Token already blacklisted");
        }
    }

    public boolean isTokenBlackListed(String token) {
        return tokenBlackListRepository.existsByToken(token);
    }
}
