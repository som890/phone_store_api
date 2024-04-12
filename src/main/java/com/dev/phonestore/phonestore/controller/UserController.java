package com.dev.phonestore.phonestore.controller;

import com.dev.phonestore.phonestore.entity.User;
import com.dev.phonestore.phonestore.service.IUserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @PostConstruct
    public void initRolesAndUsers() {
        userService.initializeRolesAndUsers();
    }

    @PostMapping("/create")
    public ResponseEntity < String > createNewUser(@RequestBody User user) {
        ResponseEntity < String > responseEntity = null;
        try {
            User newUser = userService.createNewUser(user);
            responseEntity = new ResponseEntity < String > ("User is created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
           logger.error("Failed to create new user", e);
            responseEntity = new ResponseEntity < > ("Unable to create new user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/adminPage")
    public String setupAdminPage() {
        return "This URL is only accessible to admin";
    }
    @GetMapping("/userPage")
    public String setupUserPage() {
        return "This URL is only accessible to user";
    }
}