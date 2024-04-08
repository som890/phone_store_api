package com.dev.phonestore.phonestore.controller;

import com.dev.phonestore.phonestore.entity.Role;
import com.dev.phonestore.phonestore.service.IRoleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<String> createNewRole(@RequestBody Role role) {
        ResponseEntity<String> responseEntity = null;
        try {
            Role newRole = roleService.createNewRole(role);
            responseEntity = new ResponseEntity<String>("Role is created", HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>("Unable to save role already", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
