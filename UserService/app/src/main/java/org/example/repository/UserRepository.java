package org.example.repository;

import java.util.Optional;

import org.example.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"userRoles.role"})
    Optional<User> findByUsername(String username);

}
