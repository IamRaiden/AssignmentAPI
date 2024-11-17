package com.example.assignment_submission_portal.repository;

import com.example.assignment_submission_portal.model.Admin;
import com.example.assignment_submission_portal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdminRepository extends MongoRepository<Admin,String> {
    List<User> findByRole(String role);

    User findByEmail(String email);

    User save (User user);
}
