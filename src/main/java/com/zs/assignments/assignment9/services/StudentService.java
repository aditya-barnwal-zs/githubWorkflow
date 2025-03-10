package com.zs.assignments.assignment9.services;

import com.zs.assignments.assignment9.dao.StudentDao;
import com.zs.assignments.assignment9.dao.StudentDaoImpl;
import com.zs.assignments.assignment9.models.Student;

/**
 * Handles business logic for student operations.
 * Calls DAO layer for database interaction.
 */
public class StudentService {
    private StudentDao studentDao;

    public StudentService() {
        studentDao = new StudentDaoImpl();
    }

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    /**
     * Creates a new student in the database.
     * @param student Student object to create.
     * @return Created student object if successful, otherwise null.
     */
    public Student createStudent(Student student) {
        return studentDao.createStudent(student);
    }

    /**
     * Fetches a student by their ID.
     * @param id Student ID.
     * @return Student object if found, otherwise null.
     */
    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }
}
