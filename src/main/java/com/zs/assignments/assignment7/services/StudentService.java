package com.zs.assignments.assignment7.services;

import com.github.javafaker.Faker;
import com.zs.assignments.assignment7.dao.StudentDao;
import com.zs.assignments.assignment7.dao.StudentDaoImpl;
import com.zs.assignments.assignment7.models.Student;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

/**
 * Service class responsible for handling student-related operations.
 */
public class StudentService {
    private static final Faker FAKER = new Faker();
    StudentDao studentDao = new StudentDaoImpl();

    /**
     * Generates a list of one million students with random names and mobile numbers.
     *
     * @return a list of randomly generated students
     */
    public ArrayList<Student> generateStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        for (int i = 1; i <= 1e6; i++) {
            Student student = new Student(i, FAKER.name().firstName(), FAKER.name().lastName(), FAKER.phoneNumber().cellPhone());
            studentList.add(student);
        }
        return studentList;
    }

    /**
     * Generates a random department ID between 1 and 3.
     *
     * @return a randomly selected department ID
     */
    public static int getRandomDepartment() {
        Random random = new Random();
        return random.nextInt(1, 4);
    }

    public void insertStudentInBulk(ArrayList<Student> studentList) {
        studentDao.insertStudentInBulk(studentList);
    }

    public ResultSet getAllStudents() {
        return studentDao.getAllStudents();
    }
}
