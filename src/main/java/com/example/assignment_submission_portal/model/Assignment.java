package com.example.assignment_submission_portal.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "assignments")
@Getter
@Setter
public class Assignment {
    @Id
    private String id; // MongoDB ID

    private String userId; // ID of the user who uploaded it
    private String adminId; // ID of the admin it's assigned to
    private String task; // Task description
    private String status; // "pending", "accepted", "rejected"
    private LocalDateTime createdAt; // Timestamp of when it was created
}
