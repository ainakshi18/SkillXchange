package com.SkillExchange.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;   // MongoDB uses String ObjectId by default

    private String name;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String profilePic;
    private String bio;
    private String location;
    private String timezone;

    private List<String> languages; // can store multiple

    private int karmaPoints = 0;
    private double trustScore = 0.0;
    private Role role = Role.USER;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastLogin;

    public enum Role { USER, ADMIN }
}
