package com.zs.assignments.assignment3.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Category extends Entity {

    public Category(String name) {
        super(name);
    }

    ArrayList<SubCategory> subCategories;
}
