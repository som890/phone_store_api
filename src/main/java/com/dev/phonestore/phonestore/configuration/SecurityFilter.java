package com.dev.phonestore.phonestore.configuration;

import com.dev.phonestore.phonestore.service.UserDetailServiceImpl;
import com.dev.phonestore.phonestore.utility.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    public static String CURRENT_USER = "";
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private static final String HEADER = "Authorization";
    private static final String FREFIX = "Bearer ";

    private JwtUtil jwtUtil;
    private  UserDetailsService userDetailService;

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        String username = null;

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            username = jwtUtil.getUsernameFromToken(token);
            CURRENT_USER = username;
            if (username != null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER);
        if (authHeader != null && authHeader.startsWith(FREFIX)) {
            return authHeader.substring(7);
        }
        return null;
    }
}