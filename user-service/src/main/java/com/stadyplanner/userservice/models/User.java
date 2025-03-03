package com.stadyplanner.userservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    @NotNull
    @Column(name="username" , unique = true , nullable = false)
    private String username;

    @NotNull
    @Column(name="password", nullable = false)
    private String password;

    @NotNull
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
}
