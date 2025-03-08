package com.stadyplanner.userservice.repositories;

import com.stadyplanner.userservice.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    boolean existsByUsername(@NotNull(message = "User name should not be null") String username);

    boolean existsByMailId(@NotNull @Email(message = "Invalid email format") String mailId);
}
