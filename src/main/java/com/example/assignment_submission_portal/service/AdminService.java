package com.example.assignment_submission_portal.service;

import com.example.assignment_submission_portal.model.Assignment;
import com.example.assignment_submission_portal.model.AssignmentStatus;
import com.example.assignment_submission_portal.model.User;
import com.example.assignment_submission_portal.repository.AdminRepository;
import com.example.assignment_submission_portal.repository.AssignmentRepository;
import com.example.assignment_submission_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Fetch all assignments
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // Approve or reject an assignment
    public String updateAssignmentStatus(String assignmentId, String status) {
        try {
            AssignmentStatus newStatus = AssignmentStatus.valueOf(status.toUpperCase());
            Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);

            if (assignmentOptional.isPresent()) {
                Assignment assignment = assignmentOptional.get();
                assignment.setStatus(newStatus.toString());
                assignmentRepository.save(assignment);
                return "Assignment status updated to: " + status;
            } else {
                return "Assignment not found with ID: " + assignmentId;
            }
        } catch (IllegalArgumentException e) {
            return "Invalid status. Please use: PENDING, ACCEPTED, or REJECTED";
        }
    }

    // Assign an admin to an assignment (optional functionality)
    public String assignAdminToAssignment(String assignmentId, String adminId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);

        if (assignmentOptional.isPresent()) {
            Assignment assignment = assignmentOptional.get();
            assignment.setAdminId(adminId); // Link admin
            assignmentRepository.save(assignment); // Save the changes
            return "Admin with ID: " + adminId + " assigned to the assignment.";
        } else {
            return "Assignment not found with ID: " + assignmentId;
        }
    }

    public User addAdmin(User user)  {
        // Check if user with same email already exists (since we know findByEmail exists)
        User existingUser = adminRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already exists");
        }

        // Set the role to "ROLE_ADMIN" (Spring Security requires this prefix)
        user.setRole("ROLE_ADMIN");

        // Hash the password using BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the new admin user
        return adminRepository.save(user);
    }

    public List<User> getAdmins() {
        return adminRepository.findByRole("ADMIN");
    }
}