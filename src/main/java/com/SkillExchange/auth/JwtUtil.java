package com.SkillExchange.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Secret key for signing JWT (ensure to keep it safe, ideally from application.properties)
    private String secret = "jdhuyqtuegfvxaweihxhevfryqfjurexhfubjtg";  // Ideally store this in application.properties

    // Expiration time (24 hours in milliseconds)
    private final long expirationMs = 86400000; // 24 hours

    /**
     * Generate a JWT token with email and role claims.
     * @param email the email of the user
     * @param role the role of the user (e.g., ROLE_USER)
     * @return the generated JWT token
     */
    public String generateToken(String email, String role,String userId) {
        return Jwts.builder()
                .setSubject(email)  // Subject of the token (user email)
                .claim("role", role) 
                .claim("userId", userId) // Custom claim for user role
                .setIssuedAt(new Date())  // Issue date of the token
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))  // Set token expiration based on 'expirationMs'
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)  // Use HS256 algorithm with secret key
                .compact();  // Compact the JWT to a string representation
    }

    /**
     * Extract claims from a JWT token.
     * @param token the JWT token
     * @return the claims from the token
     */
    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()  // Use parserBuilder for improved security
                    .setSigningKey(secret.getBytes())  // Set the signing key to validate the token
                    .build()
                    .parseClaimsJws(token)  // Parse the JWT
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    /**
     * Validate a JWT token.
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            extractClaims(token);  // Try extracting claims to verify the token
            return true;  // If no exception was thrown, the token is valid
        } catch (IllegalArgumentException e) {
            return false;  // If there's any issue with the token, it's invalid
        }
    }

    /**
     * Extract the email from a JWT token.
     * @param token the JWT token
     * @return the email from the token
     */
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();  // The email is stored as the subject in the JWT
    }

    /**
     * Extract the role from a JWT token.
     * @param token the JWT token
     * @return the role from the token
     */
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);  // Extract role from custom claim "role"
    }

    /**
     * Extract the username from a JWT token.
     * @param token the JWT token
     * @return the username from the token (stored as the subject)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);  // Extract username from the "subject" claim
    }
    public String extractName(String token) {
        return extractClaims(token).get("name", String.class); // Extract name from custom claim "name"
    }

    /**
     * Extract a specific claim from the JWT token.
     * @param token the JWT token
     * @param claimsResolver a function to extract the claim
     * @param <T> the type of the claim
     * @return the claim value
     */
    private <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractClaims(token);  // Extract all claims
        return claimsResolver.resolve(claims);  // Extract the specific claim
    }

    /**
     * Extract the user ID from the JWT token.
     * @param token the JWT token
     * @return the user ID from the token (stored as a custom claim)
     */
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));  // Extract userId from custom claim "userId"
    }

    // Functional interface for claim extraction
    @FunctionalInterface
    private interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
