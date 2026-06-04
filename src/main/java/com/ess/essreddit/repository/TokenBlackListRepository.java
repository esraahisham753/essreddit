package com.ess.essreddit.repository;

import com.ess.essreddit.model.TokenBlackList;
import com.ess.essreddit.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    Optional<TokenBlackList> findByVerificationToken(VerificationToken verificationToken);
}
