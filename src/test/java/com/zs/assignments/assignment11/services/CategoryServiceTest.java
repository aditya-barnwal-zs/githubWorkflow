package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.CategoryResponse;
import com.zs.assignments.assignment11.dto.ProductResponse;
import com.zs.assignments.assignment11.dto.ResponseMapper;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import com.zs.assignments.assignment11.exceptions.CategoryAlreadyExistsException;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import com.zs.assignments.assignment11.repository.ProductRepository;
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
    private ProductRepository productRepository;

    @Mock
    private ResponseMapper responseMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private Category category1;
    private Category category2;
    private CategoryResponse categoryResponse1;
    private CategoryResponse categoryResponse2;

    private Product product1;
    private Product product2;
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Clothing");

        categoryResponse1 = new CategoryResponse();
        categoryResponse1.setId(1L);
        categoryResponse1.setName("Electronics");

        categoryResponse2 = new CategoryResponse();
        categoryResponse2.setId(2L);
        categoryResponse2.setName("Clothing");

        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(999.99);
        product1.setCategory(category);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Smartphone");
        product2.setPrice(599.99);
        product2.setCategory(category);

        productResponse1 = new ProductResponse();
        productResponse1.setId(1L);
        productResponse1.setName("Laptop");
        productResponse1.setPrice(999.99);
        productResponse1.setCategoryId(1L);

        productResponse2 = new ProductResponse();
        productResponse2.setId(2L);
        productResponse2.setName("Smartphone");
        productResponse2.setPrice(599.99);
        productResponse2.setCategoryId(1L);
    }

    @Test
    void shouldGetAllCategories() {
        List<Category> categories = Arrays.asList(category1, category2);
        List<CategoryResponse> expectedDTOs = Arrays.asList(categoryResponse1, categoryResponse2);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(responseMapper.toCategoryDTOs(categories)).thenReturn(expectedDTOs);

        List<CategoryResponse> result = categoryService.getAllCategories();

        assertEquals(expectedDTOs, result);
        verify(categoryRepository).findAll();
        verify(responseMapper).toCategoryDTOs(categories);
    }

    @Test
    void shouldGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(responseMapper.toCategoryDTO(category1)).thenReturn(categoryResponse1);

        CategoryResponse result = categoryService.getCategoryById(1L);

        assertEquals(categoryResponse1, result);
        verify(categoryRepository).findById(1L);
        verify(responseMapper).toCategoryDTO(category1);
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
    void shouldGetAllProductsByCategoryId() {
        List<Product> products = Arrays.asList(product1, product2);
        List<ProductResponse> expectedDTOs = Arrays.asList(productResponse1, productResponse2);

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findByCategoryId(1L)).thenReturn(products);
        when(responseMapper.toProductDTOs(products)).thenReturn(expectedDTOs);

        List<ProductResponse> result = categoryService.getAllProductsByCategoryId(1L);

        assertEquals(expectedDTOs, result);
        verify(categoryRepository).existsById(1L);
        verify(productRepository).findByCategoryId(1L);
        verify(responseMapper).toProductDTOs(products);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFoundForProducts() {
        when(categoryRepository.existsById(999L)).thenReturn(false);

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getAllProductsByCategoryId(999L)
        );

        assertEquals("Category not found with id: 999", exception.getMessage());
        verify(categoryRepository).existsById(999L);
        verify(productRepository, never()).findByCategoryId(any());
    }

    @Test
    void shouldCreateCategory() {
        CategoryResponse inputDTO = new CategoryResponse();
        inputDTO.setName("New Category");

        Category newCategory = new Category();
        newCategory.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId(3L);
        savedCategory.setName("New Category");

        CategoryResponse expectedDTO = new CategoryResponse();
        expectedDTO.setId(3L);
        expectedDTO.setName("New Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
        when(responseMapper.toCategoryDTO(savedCategory)).thenReturn(expectedDTO);

        CategoryResponse result = categoryService.createCategory(inputDTO);

        assertEquals(expectedDTO, result);
        verify(categoryRepository).save(any(Category.class));
        verify(responseMapper).toCategoryDTO(savedCategory);
    }

    @Test
    void shouldDeleteCategory() {
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository).existsById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCategory() {
        Long categoryId = 999L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.deleteCategory(categoryId)
        );

        assertEquals("Category not found with id: 999", exception.getMessage());
        verify(categoryRepository).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }

    @Test
    void shouldThrowExceptionWhenCreatingCategoryWithExistingName() {
        CategoryResponse inputDTO = new CategoryResponse();
        inputDTO.setName("Existing Category");

        when(categoryRepository.existsByName("Existing Category")).thenReturn(true);

        CategoryAlreadyExistsException exception = assertThrows(
                CategoryAlreadyExistsException.class,
                () -> categoryService.createCategory(inputDTO)
        );

        assertEquals("Category with this name already exists", exception.getMessage());
        verify(categoryRepository).existsByName("Existing Category");
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
