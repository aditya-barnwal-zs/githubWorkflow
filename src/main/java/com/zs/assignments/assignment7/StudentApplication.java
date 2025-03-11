package com.zs.assignments.assignment7;

import com.zs.assignments.assignment7.controllers.StudentController;

/**
 * Entry point of the application.
 */
public class StudentApplication {
    public static void main(String[] args) {

        StudentController studentController = new StudentController();
        studentController.runProgram();
    }
}
