package org.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.entity.UserRole;
import org.example.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    // public void assignUserRole(Long userId,Role role) {
    //     userRoleRepository.save(UserRole.builder().userId(userId).roleId(role.getId()).build());
    // }

    public List<UserRole> assignUserRoles(User user) {
        List<Role> roles = roleService.findByType(user.getUserType());
        List<UserRole>userRoles = roles.stream().map(role -> UserRole.builder().user(user).role(role).build()).collect(Collectors.toList());
        return userRoleRepository.saveAll(userRoles);
    }

    public List<String> getRolesForUser(Long userId) {
        return userRoleRepository.findByUserId(userId).stream().map(UserRole::getRole).map(Role::getName).collect(Collectors.toList());
    }
}
