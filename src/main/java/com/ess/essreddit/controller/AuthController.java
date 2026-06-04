package com.ess.essreddit.controller;

import com.ess.essreddit.dto.AuthenticationResponse;
import com.ess.essreddit.dto.LoginRequest;
import com.ess.essreddit.dto.RefreshTokenRequest;
import com.ess.essreddit.dto.RegisterRequest;
import com.ess.essreddit.services.AuthService;
import com.ess.essreddit.services.RefreshTokenService;
import com.ess.essreddit.services.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlackListService tokenBlackListService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        authService.verifyToken(token);

        return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest, HttpServletRequest request) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7);
            tokenBlackListService.blackListToken(token);
        }
        
        return ResponseEntity.status(HttpStatus.OK)
                .body("Refresh token deleted successfully!");
    }
}
