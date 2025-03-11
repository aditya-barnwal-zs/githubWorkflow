package com.zs.assignments.assignment7.dao;

import com.zs.assignments.assignment7.models.Department;

import java.util.ArrayList;

public interface DepartmentDao {
    public void addDepartmentInBulk(ArrayList<Department> departmentList);
}
