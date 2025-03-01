package com.studyplanner.authservice.services;

import com.studyplanner.authservice.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {

        //authenticationManager checks credentials i.e authenticate user

        // DaoAuthenticationProvider - > UserDetailsService -> load user from DB

        // if password matches

        // JwtUtil generates token

        return null;
    }
}

public ResponseEntity<?> login(LoginRequest loginRequest) {
    try {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Generate token
        String token = jwtUtil.generateToken(loginRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
    }
}

