package com.stadyplanner.userservice.services;

import com.stadyplanner.userservice.models.User;

import java.util.Optional;

public interface UserService {

    User createUser(User user) ;

    Optional<User> getUserByUsername(String username);

    boolean deleteUser(long userId);

    User updateUser(long userId,User user);


    boolean findUser(long userId);
}
