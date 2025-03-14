package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.CategoryDTO;
import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    DTOMapper dtoMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DTOMapper dtoMapper) {
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<CategoryDTO> getAllCategories(){
        return dtoMapper.toCategoryDTOs(categoryRepository.findAll());
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Category category= new Category();
        category.setName(categoryDTO.getName());
        return dtoMapper.toCategoryDTO(categoryRepository.save(category));
    }
}
