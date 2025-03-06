package com.zs.assignments.assignment7.services;

import com.zs.assignments.assignment7.models.Department;

import java.util.ArrayList;

/**
 * Service class responsible for handling department-related operations.
 */
public class DepartmentService {

    /**
     * Generates a predefined list of departments.
     *
     * @return a list of departments with their respective IDs and names
     */
    public ArrayList<Department> generateDepartments() {
        ArrayList<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1, "CSE"));
        departmentList.add(new Department(2, "Mech"));
        departmentList.add(new Department(3, "EE"));
        return departmentList;
    }
}
