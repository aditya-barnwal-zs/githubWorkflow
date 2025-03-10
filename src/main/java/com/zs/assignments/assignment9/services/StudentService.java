package com.zs.assignments.assignment9.services;

import com.zs.assignments.assignment9.dao.StudentDao;
import com.zs.assignments.assignment9.dao.StudentDaoImpl;
import com.zs.assignments.assignment9.models.Student;

import java.sql.ResultSet;

public class StudentService {
    private StudentDao studentDao;

    public StudentService() {
        studentDao = new StudentDaoImpl();
    }

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student createStudent(Student student) {
        return studentDao.createStudent(student);
    }

    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }
}
