package org.example.repository;

import java.util.List;

import org.example.entity.UserRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @EntityGraph(attributePaths = {"role.name"})
    List<UserRole> findByUserId(Long userId);
}
