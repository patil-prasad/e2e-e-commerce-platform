package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.example.entity.Role;
import org.example.model.RoleRequestDTO;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/user/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public ResponseEntity getRoles() {
        try {
            List<Role> roles = roleService.getAllRoles();
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 
        new ResponseEntity<>("Error while fetching roles", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("")
    public ResponseEntity insertRole(@RequestBody RoleRequestDTO roleDto) {
        try {
            return new ResponseEntity<>(roleService.saveRole(Role.builder().name(roleDto.getName()).type(roleDto.getType()).build()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error in InsertRole", HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
    
    
}
