package com.padelsync.padelsync_core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.padelsync.padelsync_core.models.AuthResponse;
import com.padelsync.padelsync_core.models.LoginRequest;
import com.padelsync.padelsync_core.security.filter.JwtAuthenticationFilter;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String token = jwtAuthenticationFilter.generateTokenResponse(authentication);

            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok(authResponse);  
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
    
}

