package com.example.StudentRegistrationSystem.service;

import com.example.StudentRegistrationSystem.model.User;
import com.example.StudentRegistrationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && user.getUserRole() == User.Role.Admin;
    }

    public boolean isFaculty(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && user.getUserRole() == User.Role.Faculty;
    }

    public boolean isStudent(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && user.getUserRole() == User.Role.Student;
    }
}
