package com.ess.essreddit.services;

import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.model.TokenBlackList;
import com.ess.essreddit.model.VerificationToken;
import com.ess.essreddit.repository.TokenBlackListRepository;
import com.ess.essreddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class TokenBlackListService {
    private final TokenBlackListRepository tokenBlackListRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public void blackListToken(String token) {
        TokenBlackList tokenBlackList = new TokenBlackList();
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                        .orElseThrow(() -> new EssRedditException("Token Not valid"));
        tokenBlackList.setVerificationToken(verificationToken);
        tokenBlackListRepository.save(tokenBlackList);
    }
}
