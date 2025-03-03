package com.stadyplanner.userservice.services;

import com.stadyplanner.userservice.models.User;
import com.stadyplanner.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
