package org.example.model;

import org.example.entity.User;

import lombok.Builder;
import lombok.Data;


@Data
public class UserResponseDTO{
    private String username;
    private String name;
    private String userType;
    private JwtResponse auth;

    public UserResponseDTO(User user, JwtResponse jwtResponse) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.userType = user.getUserType();
        this.auth = jwtResponse;
    }
}
