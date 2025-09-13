package com.SkillExchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.SkillExchange.auth.JwtAuthFilter;
import com.SkillExchange.service.CustomUserDetailsService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .cors().and() // Enable CORS globally
//            .authorizeRequests()
//            .requestMatchers("/api/auth/**").permitAll()  // Allow public access to auth endpoints
//            .requestMatchers("/api/volunteers/list").authenticated()  // Only authenticated users can access this
//            .requestMatchers("/api/donations/organizations").hasRole("USER")  // Only authenticated users can access this
//            .requestMatchers("/server1/**").permitAll()  // Allow server1 endpoints without authentication
//            .anyRequest().authenticated()  // Protect all other endpoints
//            .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless session (no session state)
//
//        // Add the JWT filter before the UsernamePasswordAuthenticationFilter
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors().and() // Enable CORS
            .authorizeRequests()
            .requestMatchers("/api/auth/**").permitAll() // Public endpoints
            .requestMatchers("/api/patient/**").authenticated() 
            .requestMatchers("/api/doctor/**").authenticated() 
            .requestMatchers("/api/order/**").authenticated()  
            .requestMatchers("/api/medicine-request/**").authenticated() 
            .requestMatchers("/api/medicines/**").authenticated() 
            .requestMatchers("/api/appointments/**").authenticated() 
            .requestMatchers("/api/prescriptions/**").authenticated() 
            .requestMatchers("/api/consultation**").authenticated() 
            .requestMatchers("/api/medicalstore/**").hasAnyRole("PATIENT", "DOCTOR", "PHARMACIST")
//
//            .requestMatchers("/api/medicalstore/**").authenticated() 
//            .requestMatchers("/api/medicalstore/**").authenticated() 
//            .requestMatchers("/api/medicalstore/all","/api/medicalstore/{id}").authenticated()
            .requestMatchers("/signup", "/login").permitAll() // Public endpoints
            .anyRequest().authenticated() // Protect all other endpoints
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // No sessions

        // Add the JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


  

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
