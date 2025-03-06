package com.zs.assignments.assignment7.controllers;

import com.zs.assignments.assignment7.models.Department;
import com.zs.assignments.assignment7.models.Student;
import com.zs.assignments.assignment7.repositories.DepartmentRepository;
import com.zs.assignments.assignment7.repositories.StudentDepartmentRepository;
import com.zs.assignments.assignment7.repositories.StudentRepository;
import com.zs.assignments.assignment7.services.DepartmentService;
import com.zs.assignments.assignment7.services.FileService;
import com.zs.assignments.assignment7.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Scanner SC = new Scanner(System.in);

    StudentRepository studentRepository = new StudentRepository();
    DepartmentRepository departmentRepository = new DepartmentRepository();
    StudentDepartmentRepository studentDepartmentRepository = new StudentDepartmentRepository();
    StudentService studentService = new StudentService();
    DepartmentService departmentService = new DepartmentService();
    FileService fileService = new FileService();

    public void runProgram() {

        ArrayList<Student> studentList = studentService.generateStudents();
        LOGGER.info("Inserting data in Student Table");
        studentRepository.insertStudentInBulk(studentList);

        ArrayList<Department> departmentList = departmentService.generateDepartments();
        LOGGER.info("Inserting data in Department Table");
        departmentRepository.addDepartmentInBulk(departmentList);

        LOGGER.info("Inserting data in StudentDepartment Table");
        studentDepartmentRepository.assignDepartment();

        ResultSet allStudentData = studentRepository.getAllStudents();

        LOGGER.info("Enter the file path where you want to save Students Data");
        String filePath = SC.next();
        fileService.saveStudentData(filePath, allStudentData);
        LOGGER.info("Student Data saved in the given path");

        LOGGER.info("Enter the file path where you want to save compressed Data");
        String compressedFilePath = SC.next();
        FileService.compressFile(filePath, compressedFilePath);
        LOGGER.info("File compressed");

        LOGGER.info("Enter the file path where you want to save de-compressed Data");
        String deCompressedFilePath = SC.next();
        FileService.decompressFile(compressedFilePath, deCompressedFilePath);
        LOGGER.info("File de-compressed");
    }
}
