package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.entity.User;

public interface IUserService {
    public void initializeRolesAndUsers();
    public User registerNewUser(User user) ;
}
