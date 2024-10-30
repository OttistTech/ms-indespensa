package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;

    public String generateToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(365, ChronoUnit.DAYS))
                .subject(user.getEmail())
                .claim("user_id", user.getUserId())
                .claim("scope", user.getType().toUpperCase())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "RS256").build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    public String extractEmail(String token) {
        return "";
    }
}

