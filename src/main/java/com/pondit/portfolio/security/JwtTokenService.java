package com.pondit.portfolio.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    /**
     * Generates a JWT token for the authenticated user.
     *
     * This method creates a JWT token with the following properties:
     * - The issuer is set to "self".
     * - The token is issued at the current time.
     * - The token expires 5 minutes after issuance.
     * - The subject of the token is the username of the authenticated user.
     * - A custom claim "scope" is added with the value "ROLE_ADMIN".
     *
     * The token is signed using the HS256 algorithm.
     *
     * @param authentication the {@link Authentication} object containing the user's authentication details
     * @return a {@link String} representing the generated JWT token
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = "ROLE_ADMIN";
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    /**
     * Extracts the expiration time from a JWT token.
     *
     * This method decodes the provided JWT token and retrieves the "exp" claim,
     * which represents the expiration time of the token. The expiration time is
     * returned as the number of milliseconds since the epoch.
     *
     * @param token the JWT token to decode
     * @return the expiration time of the token in milliseconds since the epoch
     */
    public Long extractExpirationTime(String token) {
        Jwt jwt = decoder.decode(token);
        var exp = (Instant) jwt.getClaim("exp");
        return exp.toEpochMilli();
    }
}
