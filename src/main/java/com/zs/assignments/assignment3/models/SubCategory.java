package com.zs.assignments.assignment3.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SubCategory extends Entity {

    ArrayList<Product> products;

    public SubCategory(String name) {
        super(name);
        this.products = new ArrayList<>();
    }
}
