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
    }
}
