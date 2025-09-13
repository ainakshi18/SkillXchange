package com.SkillExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.SkillExchange.model.BaseUser.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseUser {
    private String username;
    private String profilePic;
    private String bio;
    private String location;
    private String timezone;

    private List<String> languages; 
    private int karmaPoints = 0;
    private double trustScore = 0.0;
    private List<String> skills;
    private Avaliability avaliable = Avaliability.FLEXIBLE;
    
    public enum Avaliability { FLEXIBLE, WEEKDAYS, WEEKENDS,EVENINGS}
    public User(BaseUser baseUser) {
        // Copy fields from BaseUser
        this.setId(baseUser.getId());
        this.setName(baseUser.getName());
        this.setEmail(baseUser.getEmail());
        this.setPassword(baseUser.getPassword());
        this.setRole(baseUser.getRole());
    }
}
