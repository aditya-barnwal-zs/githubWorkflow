package com.zs.assignments.assignment9;


import com.zs.assignments.assignment9.controllers.StudentController;

public class MainApplication {
    public static void main(String[] args) {
        StudentController studentController = new StudentController();
        studentController.runProgram();
    }
}
