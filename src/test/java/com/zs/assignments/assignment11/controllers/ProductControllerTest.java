package com.zs.assignments.assignment11.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.dto.ProductDTO;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.exceptions.ProductAlreadyExistsException;
import com.zs.assignments.assignment11.exceptions.ProductNotFoundException;
import com.zs.assignments.assignment11.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private DTOMapper dtoMapper;

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;

    @BeforeEach
    void setUp() {
        productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        productDTO1.setName("Laptop");
        productDTO1.setPrice(999.99);
        productDTO1.setCategoryId(1L);

        productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setName("Smartphone");
        productDTO2.setPrice(599.99);
        productDTO2.setCategoryId(1L);
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        List<ProductDTO> products = Arrays.asList(productDTO1, productDTO2);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Laptop")))
                .andExpect(jsonPath("$[0].price", is(999.99)))
                .andExpect(jsonPath("$[0].categoryId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Smartphone")))
                .andExpect(jsonPath("$[1].price", is(599.99)))
                .andExpect(jsonPath("$[1].categoryId", is(1)));
    }

    @Test
    void shouldGetProductsByCategoryId() throws Exception {
        List<ProductDTO> products = Arrays.asList(productDTO1, productDTO2);
        when(productService.getAllProductsByCategoryId(1L)).thenReturn(products);

        mockMvc.perform(get("/api/v1/products/by-category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void shouldReturn404WhenCategoryNotFoundForProducts() throws Exception {
        when(productService.getAllProductsByCategoryId(999L)).thenThrow(new CategoryNotFoundException(999L));

        mockMvc.perform(get("/api/v1/products/by-category/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("New Product");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(1L);

        ProductDTO responseDTO = new ProductDTO();
        responseDTO.setId(3L);
        responseDTO.setName("New Product");
        responseDTO.setPrice(799.99);
        responseDTO.setCategoryId(1L);

        when(productService.createProduct(any(ProductDTO.class), anyLong())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("New Product")))
                .andExpect(jsonPath("$.price", is(799.99)))
                .andExpect(jsonPath("$.categoryId", is(1)));
    }

    @Test
    void shouldReturn400WhenInvalidProductInput() throws Exception {
        ProductDTO invalidDTO = new ProductDTO();
        invalidDTO.setPrice(799.99);
        invalidDTO.setCategoryId(1L);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setId(1L);
        inputDTO.setName("Updated Laptop");
        inputDTO.setPrice(1299.99);
        inputDTO.setCategoryId(1L);

        ProductDTO responseDTO = new ProductDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Updated Laptop");
        responseDTO.setPrice(1299.99);
        responseDTO.setCategoryId(1L);

        when(productService.updateProduct(any(ProductDTO.class), anyLong(), anyLong())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Laptop")))
                .andExpect(jsonPath("$.price", is(1299.99)))
                .andExpect(jsonPath("$.categoryId", is(1)));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Response should be 204 No Content as per the controller
    }

    @Test
    void shouldReturn404WhenDeleteNonExistentProduct() throws Exception {
        doThrow(new ProductNotFoundException(999L)).when(productService).deleteProduct(999L);

        mockMvc.perform(delete("/api/v1/products/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
