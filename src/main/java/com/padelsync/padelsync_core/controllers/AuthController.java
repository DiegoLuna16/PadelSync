package com.padelsync.padelsync_core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.padelsync.padelsync_core.dtos.ApiResponse;
import com.padelsync.padelsync_core.dtos.UserDTO;
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
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Usar AuthenticationManager para autenticar
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            
            // Generar el token
            UserDTO userDTO = jwtAuthenticationFilter.generateTokenResponse(authentication);
            
            // Crear respuesta
            ApiResponse<UserDTO> response = new ApiResponse<>(userDTO, "Login exitoso", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En caso de error
            ApiResponse<UserDTO> errorResponse = new ApiResponse<>(null, "Error en la autenticación. Username puto o password incorrectos!", "error");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}
