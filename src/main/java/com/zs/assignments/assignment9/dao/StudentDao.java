package com.zs.assignments.assignment9.dao;

import com.zs.assignments.assignment9.models.Student;

public interface StudentDao {

    Student createStudent(Student student);

    Student getStudentById(int id);
}
