package com.zs.assignments.assignment9.controllers;

import com.zs.assignments.assignment9.models.Student;
import com.zs.assignments.assignment9.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles student-related actions.
 * Calls the service layer to perform student operations.
 */
public class StudentController {
    private final Logger LOGGER = LogManager.getLogger(StudentController.class);
    StudentService studentService;

    public StudentController() {
        this.studentService = new StudentService();
    }

    /**
     * Runs the program by creating a student and fetching details.
     */
    public void runProgram() {
        LOGGER.info("Inserting a new Student in Table");
        Student student = new Student(1, "Aditya", "Barnwal");
        Student student1 = studentService.createStudent(student);
        LOGGER.info("{} | {} | {}", student1.getId(), student1.getFirstName(), student1.getLastName());

        LOGGER.info("Getting user details with id 1");
        student1 = studentService.getStudentById(1);
        LOGGER.info("{} | {} | {}", student1.getId(), student1.getFirstName(), student1.getLastName());
    }
}
