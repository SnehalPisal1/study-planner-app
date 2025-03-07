package com.stadyplanner.userservice.services;

import com.stadyplanner.userservice.models.User;
import com.stadyplanner.userservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices{

    private final static Logger LOGGER= LoggerFactory.getLogger(UserServicesImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        LOGGER.info("Get User By Username : "+username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(long userId) {
    }

    @Override
    public User updateUser(long userId, User user) {
        return null;
    }
}
