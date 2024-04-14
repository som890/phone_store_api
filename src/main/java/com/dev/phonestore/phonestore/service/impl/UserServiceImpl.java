package com.dev.phonestore.phonestore.service.impl;

import com.dev.phonestore.phonestore.entity.Role;
import com.dev.phonestore.phonestore.entity.User;
import com.dev.phonestore.phonestore.exception.RoleNotFoundException;
import com.dev.phonestore.phonestore.repository.RoleRepository;
import com.dev.phonestore.phonestore.repository.UserRepository;
import com.dev.phonestore.phonestore.service.IUserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void initializeRolesAndUsers() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("User role");
        roleRepository.save(userRole);

        adminRole = roleRepository.findById(adminRole.getRoleId()).orElseThrow(() -> new RuntimeException("Admin not found"));
        userRole = roleRepository.findById(userRole.getRoleId()).orElseThrow(() -> new RuntimeException("User not found"));

        User adminUser = new User();
        adminUser.setUserName("Som890");
        adminUser.setUserFirstName("Lo");
        adminUser.setUserLastName("Som");
        adminUser.setUserPassword(passwordEncoder.encode("@0098LvSomnn@@"));
        Set<Role> adminListRole = new HashSet<>();
        adminListRole.add(adminRole);
        adminUser.setRoles(adminListRole);
        userRepository.save(adminUser);
        logger.info("Save User: {}", adminUser);
    }

    @Override
    public User registerNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        Optional<Role> roleOption = roleRepository.findByRoleName("User");
        Role role = roleOption.orElseThrow(() -> new RoleNotFoundException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        return userRepository.save(user);
    }

}
