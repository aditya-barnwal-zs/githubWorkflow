package com.zs.assignments.assignment7.dao;

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
public class StudentDaoImpl implements StudentDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Connection CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();

    /**
     * Inserts multiple students into the database in bulk.
     *
     * @param studentList a list of students to be added
     */
    @Override
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
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Fetch all students along with their department names.
     *
     * @return a ResultSet containing student details with department names
     */
    @Override
    public ResultSet getAllStudents() {
        String query = "SELECT s.id, firstName, lastName, mobileNumber, d.name FROM Student as s JOIN StudentDepartment as dm ON s.id=dm.studentId JOIN Department as d ON dm.departmentId=d.id ORDER BY s.id";
        try {
            Statement statement = CONNECTION.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Assigns a random department to each student in the database.
     */
    @Override
    public void assignDepartment() {

        try {
            String query = "SELECT id from Student";
            ResultSet studentId;
            Statement statement = CONNECTION.createStatement();
            studentId = statement.executeQuery(query);

            String insertQuery = "INSERT INTO StudentDepartment (studentId, departmentId) VALUES (?, ?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(insertQuery);
            CONNECTION.setAutoCommit(false);

            while (studentId.next()) {
                preparedStatement.setInt(1, studentId.getInt("id"));
                preparedStatement.setInt(2, getRandomDepartment());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            CONNECTION.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
