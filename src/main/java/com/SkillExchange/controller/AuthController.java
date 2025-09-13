package com.SkillExchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.SkillExchange.auth.JwtUtil;
import com.SkillExchange.auth.LoginRequest;
import com.SkillExchange.model.User;
import com.SkillExchange.model.BaseUser;
import com.SkillExchange.model.JwtResponse;
import com.SkillExchange.repository.BaseUserRepository;
import com.SkillExchange.service.BaseUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BaseUserRepository baseuserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BaseUserService baseuserService;

    // **Signup method** - Register new users and return a JWT token
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody BaseUser baseuser) {
        // Check if the email is already registered
        if (baseuserRepository.findByEmail(baseuser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        // Encode the password
        baseuser.setPassword(passwordEncoder.encode(baseuser.getPassword()));

//        if (user.getRole() == null) {
//            user.setRole(BaseUser.role.USER); // Default role
//        }
        if (baseuser.getRole() == null) {
        	baseuser.setRole(BaseUser.Role.USER); // ✅ reference inner enum
        }

        // Save the user to the database
        baseuserRepository.save(baseuser);

        // Generate JWT token for the newly registered user
        String role = baseuser.getRole().name(); // Get the role assigned to the user
        String token = jwtUtil.generateToken(baseuser.getEmail(), role, baseuser.getId());

        // Create a response with the JWT and user details
        JwtResponse response = new JwtResponse(
                token,
                baseuser.getId(),
                baseuser.getEmail(),
                baseuser.getName(),
                role // Include role in the response
        );

        return ResponseEntity.ok(response); // Return response with JWT token
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
//        try {
//            // Authenticate the user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//            );
//
//            // Set authentication context
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Retrieve user details from authentication object
//            org.springframework.security.core.userdetails.BaseUser userDetails =
//                    (org.springframework.security.core.userdetails.BaseUser) authentication.getPrincipal();
//
//            // Fetch additional user details from your UserService
//            BaseUser user = userService.findByEmail(userDetails.getUsername());
//            if (user == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//            }
//
//            // Retrieve the user's role
//            String role = authentication.getAuthorities().iterator().next().getAuthority(); // Get the first role
//
//            // Generate JWT token (include userId)
//            String token = jwtUtil.generateToken(user.getEmail(), role, user.getId());
//
//            // Return response with JWT, user details, and role
//            JwtResponse response = new JwtResponse(
//                    token,
//                    user.getId(),
//                    user.getEmail(),
//                    user.getName(),
//                    role // Include role in response
//            );
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user with Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
          

            // Save authentication in context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // ✅ Get Spring Security User object
            org.springframework.security.core.userdetails.User userDetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            // ✅ Fetch the actual BaseUser from DB (Mongo)
         // Fetch BaseUser from DB using Optional
            BaseUser user = baseuserRepository.findByEmail(userDetails.getUsername())
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // ✅ Extract role (granted authority)
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            // ✅ Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), role, user.getId());

            // ✅ Build and return response
            JwtResponse response = new JwtResponse(
                    token,
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    role
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}
