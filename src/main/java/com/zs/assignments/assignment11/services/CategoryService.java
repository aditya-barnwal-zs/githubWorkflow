package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.CategoryDTO;
import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.exceptions.CategoryAlreadyExistsException;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Category operations
 * Handles business logic related to categories
 */
@Service
public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final DTOMapper dtoMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, DTOMapper dtoMapper) {
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Get all categories
     *
     * @return List of all categories as DTOs
     */
    public List<CategoryDTO> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.debug("Found {} categories", categories.size());
        return dtoMapper.toCategoryDTOs(categories);
    }

    /**
     * Get a category by its ID
     *
     * @param id Category ID
     * @return Category as DTO
     * @throws CategoryNotFoundException if category is not found
     */
    public CategoryDTO getCategoryById(Long id) {
        logger.info("Fetching category with ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new CategoryNotFoundException(id);
                });

        logger.debug("Found category: {}", category.getName());
        return dtoMapper.toCategoryDTO(category);
    }

    /**
     * Create a new category
     *
     * @param categoryDTO Category information
     * @return Created category as DTO
     */
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        logger.info("Creating new category: {}", categoryDTO.getName());

        Category category = new Category();
        category.setName(categoryDTO.getName());
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new CategoryAlreadyExistsException("Category with this name already exists");
        }
        Category createdCategory = categoryRepository.save(category);
        logger.info("Category created successfully with ID: {}", createdCategory.getId());

        return dtoMapper.toCategoryDTO(createdCategory);
    }

    /**
     * Delete a category
     *
     * @param categoryId Product ID
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            logger.error("Category not found with ID: {}", categoryId);
            throw new CategoryNotFoundException(categoryId);
        }

        categoryRepository.deleteById(categoryId);
        logger.info("Category deleted successfully: {}", categoryId);
    }
}
