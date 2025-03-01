package com.studyplanner.authservice.services;

import com.studyplanner.authservice.dto.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

        ResponseEntity<?> login(LoginRequest loginRequest);

}
