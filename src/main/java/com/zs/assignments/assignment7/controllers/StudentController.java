package com.zs.assignments.assignment7.controllers;

import com.zs.assignments.assignment7.models.Department;
import com.zs.assignments.assignment7.models.Student;
import com.zs.assignments.assignment7.services.DepartmentService;
import com.zs.assignments.assignment7.services.FileService;
import com.zs.assignments.assignment7.services.StudentDepartmentService;
import com.zs.assignments.assignment7.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentController {
    private static final Logger LOGGER = LogManager.getLogger();

    private StudentService studentService;
    private DepartmentService departmentService;
    private StudentDepartmentService studentDepartmentService;

    public StudentController() {
        studentService = new StudentService();
        departmentService = new DepartmentService();
        studentDepartmentService = new StudentDepartmentService();
    }

    public void insertRecords() {

        ArrayList<Student> studentList = studentService.generateStudents();
        LOGGER.info("Inserting data in Student Table");
        studentService.insertStudentInBulk(studentList);

        ArrayList<Department> departmentList = departmentService.generateDepartments();
        LOGGER.info("Inserting data in Department Table");
        departmentService.addDepartmentInBulk(departmentList);

        LOGGER.info("Inserting data in StudentDepartment Table");
        studentDepartmentService.assignDepartment();
    }
}
