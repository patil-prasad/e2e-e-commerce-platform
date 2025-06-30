package org.example.service;

import java.util.List;
import java.util.Optional;

import org.example.entity.Role;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findByType(String type) {
        return roleRepository.findByType(type);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
