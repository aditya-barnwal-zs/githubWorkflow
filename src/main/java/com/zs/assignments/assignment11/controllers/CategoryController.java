package com.zs.assignments.assignment11.controllers;

import com.zs.assignments.assignment11.dto.CategoryDTO;
import com.zs.assignments.assignment11.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping(path = "/create")
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return categoryService.createCategory(categoryDTO);
    }
}
