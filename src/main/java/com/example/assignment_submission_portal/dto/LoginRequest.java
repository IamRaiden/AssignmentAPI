package com.example.assignment_submission_portal.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Or just @Data if you want all Lombok features
public class LoginRequest {
    private String email;
    private String password;


}