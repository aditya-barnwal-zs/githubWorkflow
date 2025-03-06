package com.zs.assignments.assignment7.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a department in an educational institution.
 * Each department has a unique ID and a name.
 */
@Getter
@Setter
public class Department {
    private int id;
    private String name;

    /**
     * Constructs a new Department instance.
     *
     * @param id   the id for the department
     * @param name the name of the department
     */
    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
