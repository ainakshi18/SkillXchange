package com.SkillExchange.model;

public class JwtResponse {
    private String token;
    private String userId;
    private String email;
    private String fullname;
    private String role; // Add role field

    public JwtResponse(String token, String userId, String email, String fullname, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.fullname = fullname;
        this.role = role; // Initialize role
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
