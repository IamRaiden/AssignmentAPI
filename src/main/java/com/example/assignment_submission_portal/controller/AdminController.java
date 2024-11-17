package com.example.assignment_submission_portal.controller;

import com.example.assignment_submission_portal.dto.LoginRequest;
import com.example.assignment_submission_portal.dto.RegisterRequest;
import com.example.assignment_submission_portal.model.Assignment;
import com.example.assignment_submission_portal.model.User;
import com.example.assignment_submission_portal.service.AdminService;
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

//import javax.management.remote.JMXAuthenticator;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;



    @Autowired
    private AuthenticationManager authenticationManager;


    // Get all assignments
    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = adminService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAdmins() {
        List<User> admins = adminService.getAdmins();
        return ResponseEntity.ok(admins);
    }


    // Update assignment status
    @PutMapping("/assignments/{assignmentId}/status")
    public ResponseEntity<String> updateAssignmentStatus(
            @PathVariable String assignmentId,
            @RequestParam String status) {
        String response = adminService.updateAssignmentStatus(assignmentId, status);
        return ResponseEntity.ok(response);
    }

    // Assign admin to an assignment
    @PutMapping("/assignments/{assignmentId}/assignAdmin")
    public ResponseEntity<String> assignAdminToAssignment(
            @PathVariable String assignmentId,
            @RequestParam String adminId) {
        String response = adminService.assignAdminToAssignment(assignmentId, adminId);
        return ResponseEntity.ok(response);
    }

    // Single endpoint for admin registration
    @PostMapping("/register")
    public ResponseEntity<User> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        User registeredAdmin = adminService.addAdmin(user);
        return ResponseEntity.ok(registeredAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
//
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}