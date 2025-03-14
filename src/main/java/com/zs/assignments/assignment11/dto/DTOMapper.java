package com.zs.assignments.assignment11.dto;

import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between entities and DTOs
 */
@Component
public class DTOMapper {

    /**
     * Convert Product entity to ProductDTO
     *
     * @param product The product entity
     * @return ProductDTO
     */
    public ProductDTO toProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }

    /**
     * Convert list of Product entities to list of ProductDTOs
     *
     * @param products List of product entities
     * @return List of ProductDTOs
     */
    public List<ProductDTO> toProductDTOs(List<Product> products) {
        return products.stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert Category entity to CategoryDTO
     *
     * @param category The category entity
     * @return CategoryDTO
     */
    public CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    /**
     * Convert list of Category entities to list of CategoryDTOs
     *
     * @param categories List of category entities
     * @return List of CategoryDTOs
     */
    public List<CategoryDTO> toCategoryDTOs(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }
}