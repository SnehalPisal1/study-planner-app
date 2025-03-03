package com.stadyplanner.userservice.controllers;

import com.stadyplanner.userservice.models.User;
import com.stadyplanner.userservice.services.UserServicesImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserServicesImpl userServicesImpl;

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){
        try{
        User response=userServicesImpl.createUser(user);
        if(response != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else{
            return ResponseEntity.badRequest().body(Map.of("message","Invalid Input"));
        }
        } catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", e));
        }
    }

}
