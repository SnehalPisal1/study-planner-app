package com.stadyplanner.userservice.services;

import com.stadyplanner.userservice.models.Role;
import com.stadyplanner.userservice.models.User;
import com.stadyplanner.userservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices{

    private final static Logger LOGGER= LoggerFactory.getLogger(UserServicesImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {

        // Validate uniqueness of username and email
        if (userRepository.existsByUsername(user.getUsername())) {
            LOGGER.info("Username already exists");
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByMailId(user.getMailId())) {
            LOGGER.info("Email already exists");
            throw new IllegalArgumentException("Email already exists");
        }

        // Hash the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        /* Ensure default role is set (if not provided)
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        } */

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        LOGGER.info("Get User By Username : "+username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(long userId, User user) {
        return null;
    }

    @Override
    public boolean findUser(long userId) {
        return userRepository.existsById(userId);
    }
}
