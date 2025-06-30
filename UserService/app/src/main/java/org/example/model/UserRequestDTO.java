package org.example.model;

import org.example.entity.User;

import lombok.Getter;


@Getter
public class UserRequestDTO{
    private String username;
    private String name;
    private String password;
    private String userType;
}
