package com.SkillExchange.service;

import com.SkillExchange.model.BaseUser;
import com.SkillExchange.model.User;
import com.SkillExchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create User from BaseUser + additional info
    public User createUser(BaseUser baseUser, User userDetails) {
        // Use BaseUser data
        User user = new User(baseUser);

        // Set additional fields
        user.setUsername(userDetails.getUsername());
        user.setBio(userDetails.getBio());
        user.setLocation(userDetails.getLocation());
        user.setTimezone(userDetails.getTimezone());
        user.setLanguages(userDetails.getLanguages());
        user.setSkills(userDetails.getSkills());
        user.setProfilePic(userDetails.getProfilePic());

        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User userDetails) {
        User user = getUserById(id);

        // Update BaseUser fields (if allowed)
        if (userDetails.getName() != null) user.setName(userDetails.getName());

        // Update additional User fields
        if (userDetails.getUsername() != null) user.setUsername(userDetails.getUsername());
        if (userDetails.getBio() != null) user.setBio(userDetails.getBio());
        if (userDetails.getLocation() != null) user.setLocation(userDetails.getLocation());
        if (userDetails.getTimezone() != null) user.setTimezone(userDetails.getTimezone());
        if (userDetails.getLanguages() != null) user.setLanguages(userDetails.getLanguages());
        if (userDetails.getSkills() != null) user.setSkills(userDetails.getSkills());
        if (userDetails.getProfilePic() != null) user.setProfilePic(userDetails.getProfilePic());
        if (userDetails.getAvaliable() != null) user.setAvaliable(userDetails.getAvaliable()); // ✅ Availability update

        user.setKarmaPoints(userDetails.getKarmaPoints());
        user.setTrustScore(userDetails.getTrustScore());

        return userRepository.save(user);
    }


    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
