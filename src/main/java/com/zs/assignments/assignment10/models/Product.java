package com.zs.assignments.assignment10.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private int price;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
