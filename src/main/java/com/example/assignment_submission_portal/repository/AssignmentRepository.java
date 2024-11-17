package com.example.assignment_submission_portal.repository;

import com.example.assignment_submission_portal.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    List<Assignment> findByAdminId(String adminId); // Get assignments for a specific admin
}
