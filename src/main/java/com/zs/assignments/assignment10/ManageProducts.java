package com.zs.assignments.assignment10;

import com.zs.assignments.assignment10.controllers.ProductController;

/**
 * Entry point of the application.
 * Calls ProductController to start operations.
 */
public class ManageProducts {
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        productController.applyOperations();
    }
}
