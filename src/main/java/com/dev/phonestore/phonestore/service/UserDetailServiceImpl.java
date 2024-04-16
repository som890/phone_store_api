package com.dev.phonestore.phonestore.service;

import com.dev.phonestore.phonestore.configuration.AuthenticationManagerProvider;
import com.dev.phonestore.phonestore.entity.JwtRequest;
import com.dev.phonestore.phonestore.entity.JwtResponse;
import com.dev.phonestore.phonestore.entity.User;
import com.dev.phonestore.phonestore.repository.UserRepository;
import com.dev.phonestore.phonestore.utility.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerProvider authenticationManagerProvider;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManagerProvider authenticationManagerProvider) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerProvider = authenticationManagerProvider;
    }

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        logger.debug("JwtToken: {}", jwtRequest);
        String userName = jwtRequest.getUserName();

        logger.debug("Username: {}",userName);
        String userPassword = jwtRequest.getUserPassword();

        authenticate(userName, userPassword);
        final UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userRepository.findById(userName).get();
        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUSerByUsername.....");
        User user = userRepository.findById(username).get();
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthorized(user));
        }else {
            logger.error("Username not found: {}", username);
            throw new UsernameNotFoundException("Username is not valid");
        }
    }

    private Set getAuthorized(User user) {
        Set < SimpleGrantedAuthority > authorities = new HashSet < > ();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManagerProvider.authenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }
    }
}