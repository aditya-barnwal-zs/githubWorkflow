package com.zs.assignments.assignment3.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Category extends Entity {

    ArrayList<SubCategory> subCategories;

    public Category(String name) {
        super(name);
        this.subCategories = new ArrayList<>();
    }
}
