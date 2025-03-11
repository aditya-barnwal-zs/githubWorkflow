package com.zs.assignments.assignment7.controllers;

import com.zs.assignments.assignment7.helper.FileCompressor;
import com.zs.assignments.assignment7.models.Department;
import com.zs.assignments.assignment7.models.Student;
import com.zs.assignments.assignment7.services.DepartmentService;
import com.zs.assignments.assignment7.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Scanner SC = new Scanner(System.in);

    private StudentService studentService;
    private DepartmentService departmentService;
    private FileCompressor fileCompressor;

    public StudentController() {
        studentService = new StudentService();
        departmentService = new DepartmentService();
        fileCompressor = new FileCompressor();
    }

    public void runProgram() {

        ArrayList<Student> studentList = studentService.generateStudents();
        LOGGER.info("Inserting data in Student Table");
        studentService.insertStudentInBulk(studentList);

        ArrayList<Department> departmentList = departmentService.generateDepartments();
        LOGGER.info("Inserting data in Department Table");
        departmentService.addDepartmentInBulk(departmentList);

        LOGGER.info("Inserting data in StudentDepartment Table");
        studentService.assignDepartment();

        ResultSet allStudentData = studentService.getAllStudents();

        LOGGER.info("Enter the file path where you want to save Students Data");
        String filePath = SC.next();
        fileCompressor.saveStudentData(filePath, allStudentData);
        LOGGER.info("Student Data saved in the given path");

        LOGGER.info("Enter the file path where you want to save compressed Data");
        String compressedFilePath = SC.next();
        FileCompressor.compressFile(filePath, compressedFilePath);
        LOGGER.info("File compressed");

        LOGGER.info("Enter the file path where you want to save de-compressed Data");
        String deCompressedFilePath = SC.next();
        FileCompressor.decompressFile(compressedFilePath, deCompressedFilePath);
        LOGGER.info("File de-compressed");
    }
}
