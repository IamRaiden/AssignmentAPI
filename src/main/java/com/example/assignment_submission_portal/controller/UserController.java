package com.example.assignment_submission_portal.controller;


import com.example.assignment_submission_portal.dto.LoginRequest;
import com.example.assignment_submission_portal.dto.RegisterRequest;
import com.example.assignment_submission_portal.model.Assignment;
import com.example.assignment_submission_portal.service.AssignmentService;
import com.example.assignment_submission_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private AssignmentService assignmentService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Register the user using the service
            userService.registerUser(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadAssignment(@RequestBody Assignment assignment) {
//        assignmentService.uploadAssignment(assignment);  // Call the method on the injected instance
//        return ResponseEntity.ok("Assignment uploaded successfully");
//    }

}