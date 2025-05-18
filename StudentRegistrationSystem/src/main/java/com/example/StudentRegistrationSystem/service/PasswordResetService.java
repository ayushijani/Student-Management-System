package com.example.StudentRegistrationSystem.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.StudentRegistrationSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String generateResetToken(String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
            userService.saveUser(user);
            return token;
        }
        return null;
    }

    public boolean validateToken(String token) {
        User user = userService.getUserByResetToken(token);
        return user != null &&
                user.getResetTokenExpiry().isAfter(LocalDateTime.now());
    }

    public boolean resetPassword(String token, String newPassword) {
        User user = userService.getUserByResetToken(token);
        if (user != null && user.getResetTokenExpiry().isAfter(LocalDateTime.now())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userService.saveUser(user);
            return true;
        }
        return false;
    }
}