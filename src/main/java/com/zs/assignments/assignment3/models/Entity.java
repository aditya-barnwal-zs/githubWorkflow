package com.zs.assignments.assignment3.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {
    protected String name;

    public Entity(String name) {
        this.name = name;
    }
}
