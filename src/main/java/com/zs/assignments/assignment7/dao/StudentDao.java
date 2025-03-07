package com.zs.assignments.assignment7.dao;

import com.zs.assignments.assignment7.models.Student;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface StudentDao {
    public void insertStudentInBulk(ArrayList<Student> studentList);

    public ResultSet getAllStudents();

}
