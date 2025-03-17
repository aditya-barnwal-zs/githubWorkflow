package com.zs.assignments.assignment9.dao;

import com.zs.assignments.assignment9.models.Student;

/**
 * Interface for Student Data Access Object (DAO).
 * Defines methods for handling Student records in the database.
 */
public interface StudentDao {

    Student createStudent(Student student);

    Student getStudentById(int id);
}
