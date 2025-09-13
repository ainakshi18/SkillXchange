package com.SkillExchange.service;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SkillExchange.repository.BaseUserRepository;
import com.SkillExchange.repository.UserRepository;
import com.SkillExchange.model.BaseUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BaseUserRepository baseuserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user by email from the database
        BaseUser user = baseuserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Ensure email and password are valid
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Validate the user role
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }

        // Return the Spring Security User object for authentication
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())) // Assign role to user
        );
    }
}
