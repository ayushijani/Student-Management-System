package com.example.StudentRegistrationSystem.controller;

import com.example.StudentRegistrationSystem.model.Student;
import com.example.StudentRegistrationSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    //create student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        System.out.println("Received student: " + student);  // Add this line to check if data is being received.
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
    }

    //read student (get all students)
    @GetMapping
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    //read student by id (get student by id)
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Optional<Student> student = studentRepository.findById(id);

        if(student.isPresent()){
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails){

        Optional<Student> studentOptional = studentRepository.findById(id);

        if(studentOptional.isPresent()){

            Student student = studentOptional.get();
            student.setName(studentDetails.getName());
            student.setDob(studentDetails.getDob());
            student.setCpi(studentDetails.getCpi());

            return new ResponseEntity<>(studentRepository.save(student),HttpStatus.OK);

        }else{

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    //delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){

        if(studentRepository.existsById(id)){

            studentRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }else{

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }



}
