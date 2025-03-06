package com.zs.assignments.assignment7.models;

import lombok.Getter;
import lombok.Setter;

/**
 * This class maintains the mapping between student IDs and department IDs.
 */
@Getter
@Setter
public class StudentDepartment {
    private int studentId;
    private int departmentId;

    /**
     * Constructs a new StudentDepartment instance.
     *
     * @param studentId   the ID of the student
     * @param departmentId the ID of the department
     */
    public StudentDepartment(int studentId, int departmentId) {
        this.studentId = studentId;
        this.departmentId = departmentId;
    }
}
