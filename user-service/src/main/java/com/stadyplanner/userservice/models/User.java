package com.stadyplanner.userservice.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private long id;
    private String username;
    private String password;
    private String role;
}
