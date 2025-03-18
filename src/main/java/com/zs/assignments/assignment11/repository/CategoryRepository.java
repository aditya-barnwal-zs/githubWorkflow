package com.zs.assignments.assignment11.repository;

import com.zs.assignments.assignment11.entity.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(@NotBlank(message = "Category name is required") String name);
}
