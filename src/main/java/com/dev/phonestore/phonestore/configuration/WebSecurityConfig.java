package com.dev.phonestore.phonestore.configuration;

import com.dev.phonestore.phonestore.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UnAuthorizedAuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailServiceImpl userDetailService;
    private final AuthenticationManagerProvider authenticationManagerProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UnAuthorizedAuthenticationEntryPoint authenticationEntryPoint, UserDetailServiceImpl userDetailService, AuthenticationManagerProvider authenticationManagerProvider, PasswordEncoder passwordEncoder) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailService = userDetailService;
        this.authenticationManagerProvider = authenticationManagerProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorized -> authorized
                        .requestMatchers("/authenticate","/register").permitAll()
                        .anyRequest().authenticated())
                        .authenticationProvider(authenticationProvider())
                        .authenticationManager(authenticationManagerProvider.authenticationManager())
                        .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}