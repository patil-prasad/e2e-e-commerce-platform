package org.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.entity.UserRole;
import org.example.entity.UserToken;
import org.example.model.JwtRequest;
import org.example.model.JwtResponse;
import org.example.model.UserRequestDTO;
import org.example.repository.UserRepository;
import org.example.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public JwtResponse saveUser(UserRequestDTO userDto) {
        User user = User.builder()
                            .username(userDto.getUsername())
                            .name(userDto.getName())
                            .password(passwordEncoder.encode(userDto.getPassword()))
                            .userType(userDto.getUserType())
                            .build();
        User userSaved = userRepository.save(user);
        userRoleService.assignUserRoles(userSaved);

        String refreshToken = jwtUserDetailsService.getRefreshTokenForUser(userSaved);
        UserToken userToken = UserToken.builder()
                                        .user(userSaved)
                                        .refreshToken(refreshToken)
                                        .build();
        userTokenRepository.save(userToken);        
        JwtResponse jwtResponse = jwtUserDetailsService.getTokenForUser(userSaved.getUsername(),refreshToken);
        return jwtResponse;
    }
    public JwtResponse login(UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userRequestDTO.getUsername());
        if(!userOptional.isPresent()) {  
            throw new RuntimeException("Username invalid");
        }
        User user = userOptional.get();
        if(!passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password invalid");
        }
        UserToken userSaved = userTokenRepository.findByUserId(user.getId());
        JwtResponse jwtResponse = jwtUserDetailsService.getTokenForUser(user.getUsername(),userSaved.getRefreshToken());
        return jwtResponse;
    }

    public User getUserByUserName(UserRequestDTO userRequestDTO) {
        return userRepository.findByUsername(userRequestDTO.getUsername()).get();
    }

    public JwtResponse getNewToken(JwtRequest jwtRequest) {
        if(jwtRequest.getRefreshToken() != null) {
            String username = jwtUserDetailsService.getUsernameFromToken(jwtRequest.getRefreshToken());
            User user = userRepository.findByUsername(username).get();
            
            return jwtUserDetailsService.getTokenForUser(username,jwtRequest.getRefreshToken() );
        }
        throw new UnsupportedOperationException("Unimplemented method 'getNewToken'");
    }
}
