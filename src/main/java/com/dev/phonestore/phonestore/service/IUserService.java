package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.entity.User;

public interface IUserService {
    public User createNewUser(User user);
    public void initializeRolesAndUsers();
}
