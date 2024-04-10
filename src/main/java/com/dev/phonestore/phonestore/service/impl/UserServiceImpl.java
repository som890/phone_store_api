package com.dev.phonestore.phonestore.service.impl;

import com.dev.phonestore.phonestore.entity.Role;
import com.dev.phonestore.phonestore.entity.User;
import com.dev.phonestore.phonestore.repository.RoleRepository;
import com.dev.phonestore.phonestore.repository.UserRepository;
import com.dev.phonestore.phonestore.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

@Service

public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void initializeRolesAndUsers() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role for web application");
        roleRepository.save(adminRole);

        User adminUser = new User();
        adminUser.setUserName("Som890");
        adminUser.setUserFirstName("Lo");
        adminUser.setUserLastName("Som");
        adminUser.setUserPassword("@0098LvSomnn@@");
        Set<Role> adminListRole = new HashSet<>();
        adminListRole.add(adminRole);
        adminUser.setRoles(adminListRole);
        userRepository.save(adminUser);


    }
}
