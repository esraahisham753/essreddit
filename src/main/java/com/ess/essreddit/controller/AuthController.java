package com.ess.essreddit.controller;

import com.ess.essreddit.dto.AuthenticationResponse;
import com.ess.essreddit.dto.LoginRequest;
import com.ess.essreddit.dto.RefreshTokenRequest;
import com.ess.essreddit.dto.RegisterRequest;
import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.services.AuthService;
import com.ess.essreddit.services.RefreshTokenService;
import com.ess.essreddit.services.TokenBlackListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@Tag(name = "Auth", description = "Auth management for Login, Signup and tokens management")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlackListService tokenBlackListService;

    @Operation(summary = "Registers a new user")
    @ApiResponse(responseCode = "200", description = "User registered successfully, and waits for verification")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @Operation(summary = "Verifying the new user account with assigned token in the email.")
    @ApiResponse(responseCode = "200", description = "User verified successfully")
    @ApiResponse(responseCode = "500", description = "Invalid token")
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        authService.verifyToken(token);

        return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
    }

    @Operation(summary = "Login with the username and password")
    @ApiResponse(responseCode = "200", description = "user logged in successfully")
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

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlackListService.blackListToken(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized access");
        }
        
        return ResponseEntity.status(HttpStatus.OK)
                .body("Refresh token deleted successfully!");
    }
}
