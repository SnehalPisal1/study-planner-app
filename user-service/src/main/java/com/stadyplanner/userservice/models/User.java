package com.stadyplanner.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    @NotNull(message = "User name should not be null")
    @Column(name="username" , unique = true , nullable = false)
    private String username;

    @NotNull
    @Column(name="password", nullable = false)
    private String password;

    @NotNull
    @Email(message = "Invalid email format")
    @Column(name="mail_id", unique = true, nullable = false)
    private String mailId;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role = Role.USER;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // Excludes password from ALL serialization (responses)
    // Explicitly define the getter and annotate it with @JsonIgnore
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty // Allows password to be set from the request
    public void setPassword(String password) {
        this.password = password;
    }
}
