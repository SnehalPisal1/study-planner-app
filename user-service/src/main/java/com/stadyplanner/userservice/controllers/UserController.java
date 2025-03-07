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


    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserByUsername(@Valid @PathVariable String username){
        Optional<User> response = userServicesImpl.getUserByUsername(username);

        if(response.isPresent()){
            return ResponseEntity.ok(response.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","User not found"));
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId){
        userServicesImpl.deleteUser(userId);
        return null;
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId, @Valid @RequestBody User user){
        User response= userServicesImpl.updateUser(userId,user);
        return null;
    }

}
