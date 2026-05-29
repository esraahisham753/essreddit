package com.ess.essreddit.security;

import com.ess.essreddit.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Data
public class JWTProvider {
    private final JwtEncoder jwtEncoder;

    @Value("${jwt.expiration.time}")
    private Long jwtExpMS;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        assert principal != null;
        return generateTokenWithUsername(principal.getUsername());
    }

    public String generateTokenWithUsername(String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpMS))
                .subject(username)
                .claims(claimsMap -> claimsMap.put("scope", "ROLE_USER"))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
