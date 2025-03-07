package com.zs.assignments.assignment7.controllers;

import com.zs.assignments.assignment7.services.FileService;
import com.zs.assignments.assignment7.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.Scanner;

/**
 * The {@code FileController} class handles file operations related to student data.
 * It interacts with {@code StudentService} to fetch student data and {@code FileService} to process file operations.
 */
public class FileController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Scanner SC = new Scanner(System.in);

    private StudentService studentService;
    private FileService fileService;

    public FileController(){
        studentService = new StudentService();
        fileService = new FileService();
    }

    /**
     * Handles file operations including saving, compressing, and decompressing student data.
     * The file paths for these operations are provided by the user.
     */
    public void fileOperations() {
        ResultSet allStudentData = studentService.getAllStudents();

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
