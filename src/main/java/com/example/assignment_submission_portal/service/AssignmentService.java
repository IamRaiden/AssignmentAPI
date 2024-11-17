package com.example.assignment_submission_portal.service;

import com.example.assignment_submission_portal.model.Assignment;
import com.example.assignment_submission_portal.model.AssignmentStatus;
import com.example.assignment_submission_portal.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;  // Remove 'static' here

    // Method to upload an assignment
    public Assignment uploadAssignment(Assignment assignment) {
        assignment.setCreatedAt(LocalDateTime.now());  // Set the creation time
        assignment.setStatus(AssignmentStatus.PENDING.toString());  // Use enum for status
        return assignmentRepository.save(assignment);  // Save to the repository
    }

    // Method to get assignment by ID
    public Assignment getAssignmentById(String assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + assignmentId));
    }

    // Method to get assignments by admin ID
    public List<Assignment> getAssignmentsByAdmin(String adminId) {
        return assignmentRepository.findByAdminId(adminId);
    }

    // Method to update an assignment
    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
}
