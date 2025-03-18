package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.dto.ProductDTO;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.exceptions.ProductAlreadyExistsException;
import com.zs.assignments.assignment11.exceptions.ProductNotFoundException;
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
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private Product product1;
    private Product product2;
    private ProductDTO productDTO1;
    private ProductDTO productDTO2;

    @BeforeEach
    void setUp() {
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
    void shouldGetAllProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        List<ProductDTO> expectedDTOs = Arrays.asList(productDTO1, productDTO2);

        when(productRepository.findAll()).thenReturn(products);
        when(dtoMapper.toProductDTOs(products)).thenReturn(expectedDTOs);

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(expectedDTOs, result);
        verify(productRepository).findAll();
        verify(dtoMapper).toProductDTOs(products);
    }

    @Test
    void shouldGetAllProductsByCategoryId() {
        List<Product> products = Arrays.asList(product1, product2);
        List<ProductDTO> expectedDTOs = Arrays.asList(productDTO1, productDTO2);

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findByCategoryId(1L)).thenReturn(products);
        when(dtoMapper.toProductDTOs(products)).thenReturn(expectedDTOs);

        List<ProductDTO> result = productService.getAllProductsByCategoryId(1L);

        assertEquals(expectedDTOs, result);
        verify(categoryRepository).existsById(1L);
        verify(productRepository).findByCategoryId(1L);
        verify(dtoMapper).toProductDTOs(products);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFoundForProducts() {
        when(categoryRepository.existsById(999L)).thenReturn(false);

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> productService.getAllProductsByCategoryId(999L)
        );

        assertEquals("Category not found with id: 999", exception.getMessage());
        verify(categoryRepository).existsById(999L);
        verify(productRepository, never()).findByCategoryId(any());
    }

    @Test
    void shouldCreateProduct() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("New Product");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("New Product")).thenReturn(null);

        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setPrice(799.99);
        newProduct.setCategory(category);

        Product savedProduct = new Product();
        savedProduct.setId(3L);
        savedProduct.setName("New Product");
        savedProduct.setPrice(799.99);
        savedProduct.setCategory(category);

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setId(3L);
        expectedDTO.setName("New Product");
        expectedDTO.setPrice(799.99);
        expectedDTO.setCategoryId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(dtoMapper.toProductDTO(savedProduct)).thenReturn(expectedDTO);

        ProductDTO result = productService.createProduct(inputDTO, 1L);

        assertEquals(expectedDTO, result);
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("New Product");
        verify(productRepository).save(any(Product.class));
        verify(dtoMapper).toProductDTO(savedProduct);
    }

    @Test
    void shouldThrowExceptionWhenCreateWithNonExistentCategory() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("New Product");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(999L);

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> productService.createProduct(inputDTO, 999L)
        );

        assertEquals("Category not found with id: 999", exception.getMessage());
        verify(categoryRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNameExists() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("Laptop");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("Laptop")).thenReturn(Optional.ofNullable(product1));

        ProductAlreadyExistsException exception = assertThrows(
                ProductAlreadyExistsException.class,
                () -> productService.createProduct(inputDTO, 1L)
        );

        assertEquals("Product with name 'Laptop' already exists", exception.getMessage());
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("Laptop");
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldUpdateProduct() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setId(1L);
        inputDTO.setName("Updated Laptop");
        inputDTO.setPrice(1299.99);
        inputDTO.setCategoryId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("Updated Laptop")).thenReturn(null);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Laptop");
        updatedProduct.setPrice(1299.99);
        updatedProduct.setCategory(category);

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Updated Laptop");
        expectedDTO.setPrice(1299.99);
        expectedDTO.setCategoryId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(dtoMapper.toProductDTO(updatedProduct)).thenReturn(expectedDTO);

        ProductDTO result = productService.updateProduct(inputDTO, 1L, 1L);

        assertEquals(expectedDTO, result);
        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("Updated Laptop");
        verify(productRepository).save(any(Product.class));
        verify(dtoMapper).toProductDTO(updatedProduct);
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        verify(productRepository).findById(1L);
        verify(productRepository).deleteById(1L);
    }


    @Test
    void shouldThrowExceptionWhenDeleteNonExistentProduct() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.deleteProduct(999L)
        );

        assertEquals("Product not found with id: 999", exception.getMessage());

        verify(productRepository).findById(999L);
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenUpdateNonExistentProduct() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setId(999L);
        inputDTO.setName("Updated Product");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(1L);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.updateProduct(inputDTO, 1L, 999L)
        );

        assertEquals("Product not found with id: 999", exception.getMessage());
        verify(productRepository).findById(999L);
        verify(categoryRepository, never()).findById(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenUpdateWithNonExistentCategory() {
        // Arrange
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setId(1L);
        inputDTO.setName("Updated Laptop");
        inputDTO.setPrice(1299.99);
        inputDTO.setCategoryId(999L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> productService.updateProduct(inputDTO, 999L, 1L)
        );

        assertEquals("Category not found with id: 999", exception.getMessage());
        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowProductAlreadyExistsExceptionWhenUpdateWithExistingName() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setId(1L);
        inputDTO.setName("Smartphone"); // Try to rename product1 to product2's name
        inputDTO.setPrice(1299.99);
        inputDTO.setCategoryId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("Smartphone")).thenReturn(Optional.of(product2));

        ProductAlreadyExistsException exception = assertThrows(
                ProductAlreadyExistsException.class,
                () -> productService.updateProduct(inputDTO, 1L, 1L)
        );

        assertEquals("Product with name 'Smartphone' already exists", exception.getMessage());
        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("Smartphone");
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldAllowUpdateToSameNameForSameProduct() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setId(1L);
        inputDTO.setName("Laptop"); // Same name as current
        inputDTO.setPrice(1299.99);
        inputDTO.setCategoryId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("Laptop")).thenReturn(Optional.of(product1));

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Laptop");
        updatedProduct.setPrice(1299.99);
        updatedProduct.setCategory(category);

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Laptop");
        expectedDTO.setPrice(1299.99);
        expectedDTO.setCategoryId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(dtoMapper.toProductDTO(updatedProduct)).thenReturn(expectedDTO);

        ProductDTO result = productService.updateProduct(inputDTO, 1L, 1L);

        assertEquals(expectedDTO, result);
        verify(productRepository).findById(1L);
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("Laptop");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldCreateProductWhenProductNameDoesNotExist() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("New Product");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("New Product")).thenReturn(Optional.empty());

        Product savedProduct = new Product();
        savedProduct.setId(3L);
        savedProduct.setName("New Product");
        savedProduct.setPrice(799.99);
        savedProduct.setCategory(category);

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setId(3L);
        expectedDTO.setName("New Product");
        expectedDTO.setPrice(799.99);
        expectedDTO.setCategoryId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(dtoMapper.toProductDTO(savedProduct)).thenReturn(expectedDTO);

        ProductDTO result = productService.createProduct(inputDTO, 1L);

        assertEquals(expectedDTO, result);
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("New Product");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldHandleNullResponseFromFindByNameDuringCreate() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("New Product");
        inputDTO.setPrice(799.99);
        inputDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByName("New Product")).thenReturn(null); // Simulate null response

        Product savedProduct = new Product();
        savedProduct.setId(3L);
        savedProduct.setName("New Product");
        savedProduct.setPrice(799.99);
        savedProduct.setCategory(category);

        ProductDTO expectedDTO = new ProductDTO();
        expectedDTO.setId(3L);
        expectedDTO.setName("New Product");
        expectedDTO.setPrice(799.99);
        expectedDTO.setCategoryId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(dtoMapper.toProductDTO(savedProduct)).thenReturn(expectedDTO);

        ProductDTO result = productService.createProduct(inputDTO, 1L);

        assertEquals(expectedDTO, result);
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByName("New Product");
        verify(productRepository).save(any(Product.class));
    }
}
