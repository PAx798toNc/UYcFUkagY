// 代码生成时间: 2025-08-16 10:19:59
package com.example.demo.service;

import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection of StudentRepository
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Retrieve a student by ID
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        student.orElseThrow(() -> new StudentNotFoundException("Student not found with id : " + id));
        return student.get();
    }

    // Save a student
    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Update a student
    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        return studentRepository.save(student);
    }

    // Delete a student
    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
