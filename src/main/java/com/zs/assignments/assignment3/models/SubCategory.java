package com.zs.assignments.assignment3.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SubCategory extends Entity {

    public SubCategory(String name) {
        super(name);
    }

    ArrayList<Product> products;
}
