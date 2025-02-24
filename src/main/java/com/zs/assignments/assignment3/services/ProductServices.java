package com.zs.assignments.assignment3.services;

import com.zs.assignments.assignment3.models.Product;
import com.zs.assignments.assignment3.models.SubCategory;

import java.util.ArrayList;
import java.util.Objects;

public class ProductServices {

    public void addProduct(SubCategory subCategory, String name, String brand, String description, int price, boolean isReturnable) {

        ArrayList<Product> productList = subCategory.getProducts();
        for (Product currentProduct : productList) {
            if (Objects.equals(currentProduct.getName(), name)) {
                System.out.println("Product with this name already exist");
                return;
            }
        }
        Product newProduct = Product.builder()
                .name(name)
                .brand(brand)
                .description(description)
                .price(price)
                .isReturnable(isReturnable)
                .build();
        productList.add(newProduct);
    }

    public void deleteProduct(SubCategory subCategory, String productName) {

        ArrayList<Product> allProductList = subCategory.getProducts();
        for (Product currentProduct : allProductList) {
            if (Objects.equals(currentProduct.getName(), productName)) {
                allProductList.remove(currentProduct);
                System.out.println("Product " + productName + " deleted succesfully");
                return;
            }
        }
        System.out.println("Product do not exist");
    }
}
