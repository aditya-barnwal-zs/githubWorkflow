package com.zs.assignments.assignment9.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a student in the system.
 * Each student has a unique ID, first name,and last name.
 */
@Getter
@Setter
public class Student {
    private int id;
    private String firstName;
    private String lastName;

    /**
     * Constructs a new Student instance.
     *
     * @param id        the ID for the student
     * @param firstName the first name of the student
     * @param lastName  the last name of the student
     */
    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
