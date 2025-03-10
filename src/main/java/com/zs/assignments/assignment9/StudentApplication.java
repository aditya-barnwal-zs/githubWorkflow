package com.zs.assignments.assignment9;


import com.zs.assignments.assignment9.controllers.StudentController;

/**
 * Entry point of the student management application.
 * Calls StudentController to run the program.
 */
public class StudentApplication {
    public static void main(String[] args) {
        StudentController studentController = new StudentController();
        studentController.runProgram();
    }
}
