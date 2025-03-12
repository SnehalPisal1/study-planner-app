package com.studyplanner.authservice.services;

import com.studyplanner.authservice.clients.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final static Logger LOGGER= LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserClient userClient; // Feign Client for User Service

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            LOGGER.info("Call User API to fetch user by username");
            ResponseEntity<?> responseEntity = userClient.getUserByUsername(username);

            LOGGER.info("Check if user exists - status code : {}", responseEntity.getStatusCode());

            if ((responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null)) {
                LOGGER.info("OK : {}", responseEntity.getBody());
                LinkedHashMap<String, String> userInfo = (LinkedHashMap<String, String>) responseEntity.getBody();
                LOGGER.info("userInfo: {}", userInfo);

                return new User(userInfo.get("username"), userInfo.get("password"), new ArrayList<>());

            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        } catch (Exception e) {
            // Handle any unexpected errors during user retrieval
            throw new UsernameNotFoundException("Error authenticating user: " + username, e);
        }
    }
}
