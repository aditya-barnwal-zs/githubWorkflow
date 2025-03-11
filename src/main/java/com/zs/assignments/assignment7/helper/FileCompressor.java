package com.zs.assignments.assignment7.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Service class for handling file-related operations such as saving, compressing, and decompressing student data.
 */
public class FileCompressor {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Saves student data from a ResultSet to a file.
     *
     * @param filePath       the path of the file to save data
     * @param allStudentData the ResultSet containing student data
     */
    public void saveStudentData(String filePath, ResultSet allStudentData) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            while (allStudentData != null && allStudentData.next()) {
                String id = allStudentData.getString(1);
                String firstName = allStudentData.getString(2);
                String lastName = allStudentData.getString(3);
                String mobileNumber = allStudentData.getString(4);
                String departmentName = allStudentData.getString(5);

                String newLine = id + " " + firstName + " " + lastName + " " + mobileNumber + " " + departmentName + " " + System.getProperty("line.separator");

                fileWriter.write(newLine);
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Compresses a file using GZIP compression.
     *
     * @param inputFile  the path of the file to be compressed
     * @param outputFile the path where the compressed file will be stored
     */
    public static void compressFile(String inputFile, String outputFile) {
        try (
                FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(outputFile);
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, bytesRead);
            }
            LOGGER.info("File compressed successfully");
        } catch (IOException e) {
            LOGGER.info("Error during compression: {}", e.getMessage());
        }
    }

    /**
     * Decompresses a GZIP-compressed file.
     *
     * @param compressedFile the path of the compressed file
     * @param outputFile     the path where the decompressed file will be stored
     */
    public static void decompressFile(String compressedFile, String outputFile) {
        try (
                FileInputStream fis = new FileInputStream(compressedFile);
                GZIPInputStream gzipIS = new GZIPInputStream(fis);
                FileOutputStream fos = new FileOutputStream(outputFile)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipIS.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("File decompressed successfully: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error during decompression: " + e.getMessage());
        }
    }
}
