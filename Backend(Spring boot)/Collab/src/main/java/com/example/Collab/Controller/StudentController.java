package com.example.Collab.Controller;


import com.example.Collab.Model.Student;
import com.example.Collab.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping("/student")
    public Student getStudentById(@RequestBody int id){
        return (Student) service.getStudentById(id);
    }

    @PostMapping("/add")
    public void addStudent(@RequestBody Student student){
        service.addStudent(student);
    }
}
