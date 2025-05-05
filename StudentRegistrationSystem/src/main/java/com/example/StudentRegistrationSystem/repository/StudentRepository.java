package com.example.StudentRegistrationSystem.repository;

import com.example.StudentRegistrationSystem.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
