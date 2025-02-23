package com.zs.assignments.assignment3.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Product extends Entity {
    private int price;
    private String brand;
    private String description;
    private boolean isReturnable;

    public Product(String name, Integer price, String brand, String description, boolean isReturnable) {
        super(name);
        this.price = price;
        this.brand = brand;
        this.description = description;
        this.isReturnable = isReturnable;
    }
}
