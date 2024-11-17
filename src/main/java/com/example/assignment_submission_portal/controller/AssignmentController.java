package com.example.assignment_submission_portal.controller;

import com.example.assignment_submission_portal.model.Assignment;
import com.example.assignment_submission_portal.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/upload")
    public ResponseEntity<Assignment> uploadAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.uploadAssignment(assignment));
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable String id) {
        return ResponseEntity.ok(assignmentService.getAssignmentById(id));
    }
}