package com.zs.assignments.assignment7;

import com.zs.assignments.assignment7.controllers.FileController;
import com.zs.assignments.assignment7.controllers.QueryController;
import com.zs.assignments.assignment7.controllers.StudentController;

/**
 * Entry point of the application.
 */
public class MainApplication {
    public static void main(String[] args) {
        StudentController studentController = new StudentController();
        studentController.insertRecords();

        FileController fileController = new FileController();
        fileController.fileOperations();

        QueryController queryController = new QueryController();
        queryController.analyzeQuery();
    }
}
