package com.zs.assignments.assignment3.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Entity {
    protected String name;

    public Entity(String name) {
        this.name = name;
    }
}
