package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByType(String type);
}
