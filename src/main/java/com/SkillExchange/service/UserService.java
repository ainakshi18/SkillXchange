package com.SkillExchange.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SkillExchange.model.User;
import com.SkillExchange.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        // Fetch user by id and handle missing user
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));  // Throws error if not found
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }


//    public String getAddressByUserId(String userId) {
//        // Fetch the user by ID
//        Optional<User> userOpt = userRepository.findById(userId);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            // Use the getAddress() method to get the address
//            return user.getAddress();  // Return the address
//        } else {
//            throw new IllegalArgumentException("User not found with id: " + userId);
//        }
//    }



}
