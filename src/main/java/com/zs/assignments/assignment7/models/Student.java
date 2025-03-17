package com.zs.assignments.assignment7.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a student in the system.
 * Each student has a unique ID, first name, last name, and mobile number.
 */
@Getter
@Setter
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;

    /**
     * Constructs a new Student instance.
     *
     * @param id           the ID for the student
     * @param firstName    the first name of the student
     * @param lastName     the last name of the student
     * @param mobileNumber the mobile number of the student
     */
    public Student(int id, String firstName, String lastName, String mobileNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
    }
}
