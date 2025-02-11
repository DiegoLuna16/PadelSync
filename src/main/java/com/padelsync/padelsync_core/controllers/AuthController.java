package com.padelsync.padelsync_core.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.padelsync.padelsync_core.dtos.UserDTO;
import com.padelsync.padelsync_core.models.AuthResponse;
import com.padelsync.padelsync_core.models.LoginRequest;
import com.padelsync.padelsync_core.models.User;
import com.padelsync.padelsync_core.security.filter.JwtAuthenticationFilter;
import com.padelsync.padelsync_core.security.service.JwtService;
import com.padelsync.padelsync_core.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtService jwtService;

    @Autowired
    UserService userService;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            String token = jwtAuthenticationFilter.generateTokenResponse(authentication);

            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserFromToken(@RequestHeader("Authorization") String token) {
        try {
            // Extract the token (remove "Bearer " prefix)
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(401).build();
            }
            String jwt = token.substring(7);

            // Extract email from token
            String email = jwtService.extractEmail(jwt);

            // Retrieve user details
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Map User to UserDTO
            UserDTO userDto = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getCountryCode(),
                    user.getBirthdate(),
                    user.getGender().getName(),
                    user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));

            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}
