package com.example.assignment_submission_portal.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin")
@Setter
@Getter
public class Admin {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
}
