package com.zs.assignments.assignment3;

import com.zs.assignments.assignment3.controller.Controller;
import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.services.CatalogueServices;

import java.util.ArrayList;

public class Catalogue {
    public static void main(String[] args) {
        CatalogueServices catalogueServices = new CatalogueServices();
        Controller controller = new Controller();

        ArrayList<Category> catalogue = catalogueServices.dummyCatalogue();

        controller.control(catalogue);
    }
}
