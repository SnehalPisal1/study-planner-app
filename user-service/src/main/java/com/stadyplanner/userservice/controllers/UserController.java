package com.stadyplanner.userservice.controllers;

import com.stadyplanner.userservice.models.User;
import com.stadyplanner.userservice.services.UserServicesImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Tag(name="User Management API" , description = "API for managing users")
@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserServicesImpl userServicesImpl;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){
        try{
        User response=userServicesImpl.createUser(user);
        if(response != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else{
            return ResponseEntity.badRequest().body(Map.of("message","Invalid Input"));
        }
        } catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@Valid @PathVariable String username){
        Optional<User> response = userServicesImpl.getUserByUsername(username);

        if(response.isPresent()){
            return ResponseEntity.ok(response.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","User not found"));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId){

        boolean exists = userServicesImpl.findUser(userId);

        Map<String, String> response = new HashMap<>();
        if(!exists){
            response.put("message","User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        else {
            response.put("message","User successfully deleted");
            userServicesImpl.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId, @Valid @RequestBody User user){
        User response= userServicesImpl.updateUser(userId,user);
        return null;
    }

}
