package com.example.StudentRegistrationSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private long userId;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    private boolean status;

    public enum Role {
        Student, Faculty, Admin
    }

}

