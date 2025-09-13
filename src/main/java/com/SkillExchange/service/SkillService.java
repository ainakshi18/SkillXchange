package com.SkillExchange.service;

import com.SkillExchange.model.Skill;
import com.SkillExchange.model.User;
import com.SkillExchange.repository.SkillRepository;
import com.SkillExchange.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaseUserService baseUserService; // to fetch BaseUser from email

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    // Create
    public Skill createSkill(Skill skill) {
        Skill savedSkill = skillRepository.save(skill);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        if (user.getSkills() == null) {
            user.setSkills(new ArrayList<>());
        }
        if (!user.getSkills().contains(savedSkill.getId())) {
            user.getSkills().add(savedSkill.getId());
        }
        userRepository.save(user);
        return savedSkill;
    }

    // Get All
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // Get by ID
    public Optional<Skill> getSkillById(String id) {
        return skillRepository.findById(id);
    }

    // Update (only non-null fields)
    public Skill updateSkill(String id, Skill updatedSkill) {
        return skillRepository.findById(id).map(skill -> {
            if (updatedSkill.getSkillName() != null) skill.setSkillName(updatedSkill.getSkillName());
            if (updatedSkill.getCategory() != null) skill.setCategory(updatedSkill.getCategory());
            if (updatedSkill.getDescription() != null) skill.setDescription(updatedSkill.getDescription());
            skill.setTrending(updatedSkill.isTrending());
            return skillRepository.save(skill);
        }).orElse(null);
    }

    public boolean deleteSkill(String id) {
        if (skillRepository.existsById(id)) {
            // Step 1: Delete from Skill repository
            skillRepository.deleteById(id);

            // Step 2: Get current user from SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            // Step 3: Remove the skill id from user's skills list
            if (user.getSkills() != null && user.getSkills().contains(id)) {
                user.getSkills().remove(id);
                userRepository.save(user);
            }

            return true;
        }
        return false;
    }
}
