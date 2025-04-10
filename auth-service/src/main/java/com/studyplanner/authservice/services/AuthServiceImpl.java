package com.studyplanner.authservice.services;

import com.studyplanner.authservice.dto.LoginRequest;
import com.studyplanner.authservice.securityUtility.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    private final static Logger LOGGER= LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {

        try {
            LOGGER.info("authenticationManager checks credentials");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),loginRequest.getPassword()));

            LOGGER.info("JwtUtil generates token");
            String token= jwtUtil.generateToken(loginRequest.getUsername());

            return ResponseEntity.ok(Map.of("AuthToken", token));

        }catch (BadCredentialsException e){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid username or password - " + e));
        }
    }
}

