package com.example.assignment_submission_portal.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Setter
@Getter
public class User {
    @Id
    private String id;  // MongoDB automatically generates this
    private String username;
    private String userId;
    private String email;
    private String password;
    private String role; // "user" or "admin"



    // Getters and setters
}
