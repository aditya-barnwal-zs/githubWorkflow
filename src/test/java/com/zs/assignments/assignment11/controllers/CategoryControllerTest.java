package com.zs.assignments.assignment11.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.assignments.assignment11.dto.CategoryDTO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private CategoryDTO categoryDTO1;
    private CategoryDTO categoryDTO2;

    @BeforeEach
    void setUp() {
        categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("Electronics");

        categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("Clothing");
    }

    @Test
    void shouldGetAllCategories() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/category")
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
        when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO1);

        mockMvc.perform(get("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Electronics")));
    }

    @Test
    void shouldReturn404WhenCategoryNotFound() throws Exception {
        when(categoryService.getCategoryById(999L)).thenThrow(new CategoryNotFoundException(999L));

        mockMvc.perform(get("/api/v1/category/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setName("New Category");

        CategoryDTO responseDTO = new CategoryDTO();
        responseDTO.setId(3L);
        responseDTO.setName("New Category");

        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("New Category")));
    }

    @Test
    void shouldReturn400WhenInvalidInput() throws Exception {
        CategoryDTO invalidDTO = new CategoryDTO();

        mockMvc.perform(post("/api/v1/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
}
