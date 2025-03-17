package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.CategoryDTO;
import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;
    private CategoryDTO categoryDTO1;
    private CategoryDTO categoryDTO2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Clothing");

        categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("Electronics");

        categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("Clothing");
    }

    @Test
    void shouldGetAllCategories() {
        List<Category> categories = Arrays.asList(category1, category2);
        List<CategoryDTO> expectedDTOs = Arrays.asList(categoryDTO1, categoryDTO2);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(dtoMapper.toCategoryDTOs(categories)).thenReturn(expectedDTOs);

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertEquals(expectedDTOs, result);
        verify(categoryRepository).findAll();
        verify(dtoMapper).toCategoryDTOs(categories);
    }

    @Test
    void shouldGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(dtoMapper.toCategoryDTO(category1)).thenReturn(categoryDTO1);

        CategoryDTO result = categoryService.getCategoryById(1L);

        assertEquals(categoryDTO1, result);
        verify(categoryRepository).findById(1L);
        verify(dtoMapper).toCategoryDTO(category1);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(999L)
        );

        assertEquals("Category not found with id: 999", exception.getMessage());
        verify(categoryRepository).findById(999L);
    }

    @Test
    void shouldCreateCategory() {
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("New Category");

        Category newCategory = new Category();
        newCategory.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId(3L);
        savedCategory.setName("New Category");

        CategoryDTO expectedDTO = new CategoryDTO();
        expectedDTO.setId(3L);
        expectedDTO.setName("New Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
        when(dtoMapper.toCategoryDTO(savedCategory)).thenReturn(expectedDTO);

        CategoryDTO result = categoryService.createCategory(inputDTO);

        assertEquals(expectedDTO, result);
        verify(categoryRepository).save(any(Category.class));
        verify(dtoMapper).toCategoryDTO(savedCategory);
    }
}
