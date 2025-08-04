package com.ems.security;


import com.ems.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/**").permitAll()
                        // Employee only endpoints
                        .requestMatchers(HttpMethod.POST,"/leaves/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/leaves/user", "/user/me/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/update/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers("/tasks/user/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        // admin only endpoints
                        .requestMatchers("/user/**",  "/leaves/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/attendance/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/payroll/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/payroll/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/payroll/all").hasRole("ADMIN")
                        .requestMatchers("/tasks/**").hasRole("ADMIN")
                        .requestMatchers("/performance/all").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
