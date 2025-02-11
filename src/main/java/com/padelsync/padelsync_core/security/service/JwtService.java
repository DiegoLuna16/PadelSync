package com.padelsync.padelsync_core.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;


import static com.padelsync.padelsync_core.security.TokenJwtConfig.SECRET_KEY;

@Service
public class JwtService {

    public String extractEmail(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(SECRET_KEY) // Use the SecretKey object
                    .build()
                    .parseSignedClaims(token);

            return claimsJws.getPayload().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid or expired token", e);
        }
    }
}