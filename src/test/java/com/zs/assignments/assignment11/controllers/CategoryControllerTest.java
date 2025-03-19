package com.zs.assignments.assignment11.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.assignments.assignment11.dto.CategoryResponse;
import com.zs.assignments.assignment11.dto.ProductResponse;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    private CategoryResponse categoryResponse1;
    private CategoryResponse categoryResponse2;

    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        categoryResponse1 = new CategoryResponse();
        categoryResponse1.setId(1L);
        categoryResponse1.setName("Electronics");

        categoryResponse2 = new CategoryResponse();
        categoryResponse2.setId(2L);
        categoryResponse2.setName("Clothing");

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
    void shouldGetAllCategories() throws Exception {
        List<CategoryResponse> categories = Arrays.asList(categoryResponse1, categoryResponse2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Electronics")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Clothing")));
    }

    @Test
    void shouldGetCategoryById() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(categoryResponse1);

        mockMvc.perform(get("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Electronics")));
    }

    @Test
    void shouldReturn404WhenCategoryNotFound() throws Exception {
        when(categoryService.getCategoryById(999L)).thenThrow(new CategoryNotFoundException(999L));

        mockMvc.perform(get("/api/v1/categories/999")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetProductsByCategoryId() throws Exception {
        List<ProductResponse> products = Arrays.asList(productResponse1, productResponse2);
        when(categoryService.getAllProductsByCategoryId(1L)).thenReturn(products);

        mockMvc.perform(get("/api/v1/categories/1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void shouldReturn404WhenCategoryNotFoundForProducts() throws Exception {
        when(categoryService.getAllProductsByCategoryId(999L)).thenThrow(new CategoryNotFoundException(999L));

        mockMvc.perform(get("/api/v1/categories/999/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryResponse inputDTO = new CategoryResponse();
        inputDTO.setName("New Category");

        CategoryResponse responseDTO = new CategoryResponse();
        responseDTO.setId(3L);
        responseDTO.setName("New Category");

        when(categoryService.createCategory(any(CategoryResponse.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("New Category")));
    }

    @Test
    void shouldReturn400WhenCategoryInvalid() throws Exception {
        CategoryResponse invalidDTO = new CategoryResponse();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Response should be 204 No Content as per the controller
    }
}
