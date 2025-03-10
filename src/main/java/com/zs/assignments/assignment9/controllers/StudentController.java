package com.zs.assignments.assignment9.controllers;

import com.zs.assignments.assignment9.models.Student;
import com.zs.assignments.assignment9.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentController {
    private final Logger LOGGER = LogManager.getLogger(StudentController.class);
    StudentService studentService;

    public StudentController() {
        this.studentService = new StudentService();
    }

    public void runProgram() {
        LOGGER.info("Inserting a new Student in Table");
        Student student = new Student(1, "Aditya", "Barnwal");
        studentService.createStudent(student);

        LOGGER.info("Getting user details with id 1");
        studentService.getStudentById(1);
    }
}
