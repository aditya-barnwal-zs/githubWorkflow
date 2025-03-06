package com.zs.assignments.assignment7.repositories;

import com.zs.assignments.assignment7.config.DatabaseConfig;
import com.zs.assignments.assignment7.models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

import static com.zs.assignments.assignment7.services.StudentService.getRandomDepartment;

/**
 * Repository class for handling database operations related to the Student entity.
 */
public class StudentRepository {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Connection CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();

    /**
     * Inserts a new student into the database.
     *
     * @param student the student object to be added
     */
    public void insertStudent(Student student) {
        String query = "INSERT INTO students (id , firstName, lastName, mobileNumber) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getMobileNumber());
            preparedStatement.execute();
            CONNECTION.commit();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Inserts multiple students into the database in bulk.
     *
     * @param studentList a list of students to be added
     */
    public void insertStudentInBulk(ArrayList<Student> studentList) {
        String query = "INSERT INTO student (id , firstName, lastName, mobileNumber) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            CONNECTION.setAutoCommit(false);
            for (Student student : studentList) {
                preparedStatement.setInt(1, student.getId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
                preparedStatement.setString(4, student.getMobileNumber());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            CONNECTION.commit();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Fetch all students along with their department names.
     *
     * @return a ResultSet containing student details with department names
     */
    public ResultSet getAllStudents() {
        String query = "SELECT s.id, firstName, lastName, mobileNumber, d.name FROM Student as s JOIN StudentDepartment as dm ON s.id=dm.studentId JOIN Department as d ON dm.departmentId=d.id ORDER BY s.id";
        try {
            Statement statement = CONNECTION.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }
}
