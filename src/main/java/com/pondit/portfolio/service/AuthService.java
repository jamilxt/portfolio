package com.pondit.portfolio.service;

import com.pondit.portfolio.model.dto.auth.AuthRequest;
import com.pondit.portfolio.model.dto.auth.AuthResponse;
import com.pondit.portfolio.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    /**
     * Authenticates a user and generates a JWT token.
     *
     * This method performs the following steps:
     * 1. Creates a {@link UsernamePasswordAuthenticationToken} using the provided {@link AuthRequest}.
     * 2. Authenticates the user using the {@link AuthenticationManager}.
     * 3. Generates a JWT token for the authenticated user using {@link JwtTokenService}.
     * 4. Extracts the expiration time of the generated JWT token.
     * 5. Returns an {@link AuthResponse} containing the JWT token, the username, and the expiration time.
     *
     * @param authRequest the authentication request containing the username and password
     * @return an {@link AuthResponse} containing the JWT token, username, and expiration time
     */
    public AuthResponse authenticate(AuthRequest authRequest) {
        var token = new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
        Authentication authentication = authenticationManager.authenticate(token);

        String jwtToken = jwtTokenService.generateToken(authentication);
        Long expiresAt = jwtTokenService.extractExpirationTime(jwtToken);

        return new AuthResponse(jwtToken, authentication.getName(), expiresAt);
    }
}
