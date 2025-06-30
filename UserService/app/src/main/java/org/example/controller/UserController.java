package org.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.example.entity.User;
import org.example.model.JwtRequest;
import org.example.model.JwtResponse;
import org.example.model.UserRequestDTO;
import org.example.model.UserResponseDTO;
import org.example.service.JwtUserDetailsService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/user")
public class UserController {
    

    @Autowired
    private UserService userService;

    // @Autowired
    // private JwtUserDetailsService jwtUserDetailsService;

    // @Autowired
    // private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserRequestDTO userDto) {
        try {
            JwtResponse jwtToken = userService.saveUser(userDto);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            JwtResponse jwtResponse = userService.login(userRequestDTO);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity postMethodName(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = userService.getNewToken(jwtRequest);
        
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }
    
    
}
