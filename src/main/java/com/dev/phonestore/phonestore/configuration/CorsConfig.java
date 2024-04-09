package com.dev.phonestore.phonestore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String UPDATE_METHOD = "PUT";
    private static final String DELETE_METHOD = "DELETE";

    @Override
    @Bean
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(GET_METHOD, POST_METHOD, UPDATE_METHOD, DELETE_METHOD)
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }
}
