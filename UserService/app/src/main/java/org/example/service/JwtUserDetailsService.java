package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.example.entity.User;
import org.example.model.JwtResponse;
import org.example.repository.UserRepository;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRoleService userRoleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public JwtResponse getTokenForUser(String username,String refreshToken) {
        UserDetails userDetails = loadUserByUsername(username);
        List<String> userRoles = getScopesFromToken(refreshToken);
        String token = jwtTokenUtil.generateToken(userDetails,userRoles);
        return new JwtResponse(token,refreshToken);
    }

    public String getRefreshTokenForUser(User user) {
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        Set<String> userRoles = userRoleService.getRolesForUser(user.getId()).stream().map(String::toLowerCase).collect(Collectors.toSet());;
        String token = jwtTokenUtil.generateRefreshToken(userDetails,userRoles);
        return token;
    }

    public String getUsernameFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return username;
    }

    public boolean validateToken(String token) {
        Boolean isValidToken = jwtTokenUtil.validateToken(token, loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token)));
        if(!isValidToken.equals(Boolean.TRUE)) {
            throw new RuntimeException("Token invalid");
        }
        return true;
    }

    public List<String> getScopesFromToken(String token) {
        return jwtTokenUtil.getClaimFromToken(token, claims -> claims.get("scopes", List.class));
    }

}