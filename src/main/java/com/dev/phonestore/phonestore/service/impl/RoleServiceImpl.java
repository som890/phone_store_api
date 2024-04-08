package com.dev.phonestore.phonestore.service.impl;

import com.dev.phonestore.phonestore.entity.Role;
import com.dev.phonestore.phonestore.repository.RoleRepository;
import com.dev.phonestore.phonestore.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
