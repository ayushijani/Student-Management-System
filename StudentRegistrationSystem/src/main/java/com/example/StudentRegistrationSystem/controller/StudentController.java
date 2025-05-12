package com.example.StudentRegistrationSystem.controller;

import com.example.StudentRegistrationSystem.model.Student;
import com.example.StudentRegistrationSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Showing form page
    @GetMapping("/students/form")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    // Showing edit form
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            model.addAttribute("student", optionalStudent.get());
            return "edit_student";
        } else {
            return "redirect:/students/list";
        }
    }

    // create
    @PostMapping("/students")
    public String submitForm(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students/list";
    }


    // read
    @GetMapping("/students/list")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        students.sort(Comparator.comparing(Student::getId));
        model.addAttribute("students", students);
        return "student_list";
    }



    // update/edit
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student studentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        System.out.println("Updating student: " + studentDetails);  // Debug
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setName(studentDetails.getName());
            student.setDob(studentDetails.getDob());
            student.setCpi(studentDetails.getCpi());
            studentRepository.save(student);
        }
        return "redirect:/students/list";
    }

    // delete
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
        return "redirect:/students/list";
    }

}
