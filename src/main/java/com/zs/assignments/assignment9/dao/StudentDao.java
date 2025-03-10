package com.zs.assignments.assignment9.dao;

import com.zs.assignments.assignment9.models.Student;

import java.sql.ResultSet;

public interface StudentDao {

    public Student createStudent(Student student);

    public Student getStudentById(int id);
}
