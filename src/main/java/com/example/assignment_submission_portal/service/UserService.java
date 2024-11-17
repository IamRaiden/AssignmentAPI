package com.example.assignment_submission_portal.service;

import com.example.assignment_submission_portal.dto.RegisterRequest;
import com.example.assignment_submission_portal.model.User;
import com.example.assignment_submission_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Handles Spring Security's user loading by email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    // New method to handle user registration
    public void registerUser(RegisterRequest registerRequest) {
        // Check if a user already exists with the same email
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("User already exists with email: " + registerRequest.getEmail());
        }

        // Create a new User entity with the provided details
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        //newUser.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "USER"); // Default role is "USER"

        // Save the new user in the database
        userRepository.save(newUser);
    }

    // Existing method to find a user by email
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public void registerAdmin(RegisterRequest registerRequest) {
        User newAdmin = new User();
        newAdmin.setUserId(registerRequest.getUsername()); // Use `username` here
        newAdmin.setEmail(registerRequest.getEmail());
        newAdmin.setPassword(registerRequest.getPassword());
        newAdmin.setRole("admin"); // Assign 'admin' role
        userRepository.save(newAdmin);
    }


}
