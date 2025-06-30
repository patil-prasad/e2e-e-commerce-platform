package org.example.service;

import org.example.entity.UserToken;
import org.example.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken findByUserId(Long id) {
        return userTokenRepository.findByUserId(id);
    }
}
