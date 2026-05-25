package com.ess.essreddit.services;

import com.ess.essreddit.dto.RegisterRequest;
import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.model.NotificationEmail;
import com.ess.essreddit.model.User;
import com.ess.essreddit.model.VerificationToken;
import com.ess.essreddit.repository.UserRepository;
import com.ess.essreddit.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailSenderService emailSenderService;


    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setCreated(Instant.now());
        newUser.setEnabled(false);
        userRepository.save(newUser);

        VerificationToken token = new VerificationToken();
        String rawToken = generateToken();
        token.setToken(rawToken);
        token.setUser(newUser);
        verificationTokenRepository.save(token);

        emailSenderService.sendEmail(new NotificationEmail(
                "Please, verify your account",
                newUser.getEmail(),
                "Thanks for signing up to essreddit. Verify your email by using this link: http://localhost:8080/api/auth/accountVerification/" + rawToken
        ));
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void verifyToken(String token) {
        Optional<VerificationToken> tokenObject = verificationTokenRepository.findByToken(token);
        VerificationToken verifiedToken = tokenObject.orElseThrow(() -> new EssRedditException("Token cannot be found"));
        fetchUserAndEnable(verifiedToken);
    }

    public void fetchUserAndEnable(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }
}
