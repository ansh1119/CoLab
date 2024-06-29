package com.example.Collab.Service;

import com.example.Collab.Model.Student;
import com.example.Collab.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public Student getStudentById(int id){
        return (Student) repo.findById(id).orElse(null);
    }

    public void addStudent(Student student){
        repo.save(student);
    }
}
