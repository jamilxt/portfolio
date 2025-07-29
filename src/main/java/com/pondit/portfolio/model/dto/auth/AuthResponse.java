package com.pondit.portfolio.model.dto.auth;

public record AuthResponse(String token, String username, Long expiresAt) {
}
