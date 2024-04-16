package com.dev.phonestore.phonestore.controller;

import com.dev.phonestore.phonestore.entity.JwtRequest;
import com.dev.phonestore.phonestore.entity.JwtResponse;
import com.dev.phonestore.phonestore.service.UserDetailServiceImpl;
import com.dev.phonestore.phonestore.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtController {

    @Autowired
    private UserDetailServiceImpl userService;

    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("JwtRequest: " + jwtRequest);
        return userService.createJwtToken(jwtRequest);
    }
}
