package com.example.assignment_submission_portal.repository;

import com.example.assignment_submission_portal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    Optional<User> findByUsername(String username);

}