package com.zs.assignments.assignment7.services;

import com.zs.assignments.assignment7.dao.StudentDepartmentDaoImpl;

public class StudentDepartmentService {
    StudentDepartmentDaoImpl studentDepartmentDaoImpl = new StudentDepartmentDaoImpl();

    public void assignDepartment(){
        studentDepartmentDaoImpl.assignDepartment();
    }
}
