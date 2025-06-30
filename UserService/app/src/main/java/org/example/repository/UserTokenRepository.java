package org.example.repository;

import java.util.Optional;

import org.example.entity.User;
import org.example.entity.UserToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByUserId(Long id);

}
