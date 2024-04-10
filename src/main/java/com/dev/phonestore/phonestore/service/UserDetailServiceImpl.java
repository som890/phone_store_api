package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.entity.User;
import com.dev.phonestore.phonestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUserName(username);
            if(user == null) {
                throw  new UsernameNotFoundException("User with username " + username + " not found");
            }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                getAuthorized(user)
        );
    }
    public Set getAuthorized(User user) {
        Set authorize = new HashSet();
        user.getRoles().forEach(role -> authorize.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        return  authorize;
    }

}
