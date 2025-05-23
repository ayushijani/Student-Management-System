package com.example.StudentRegistrationSystem.repository;

import com.example.StudentRegistrationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUserId(long userId);
    User findByResetToken(String resetToken);
}