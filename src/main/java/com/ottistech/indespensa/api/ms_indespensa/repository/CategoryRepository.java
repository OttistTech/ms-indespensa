package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.categoryName) = LOWER(:categoryName)")
    Optional<Category> findByCategoryName(@Param("categoryName") String categoryName);

    List<Category> findByCategoryNameContainingIgnoreCase(String pattern);
}
