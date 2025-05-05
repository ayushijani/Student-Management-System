package com.example.StudentRegistrationSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name="student")
public class Student {

    @Id
    private Long id;

    private String name;

    private LocalDate dob;

    private Double cpi;

}
