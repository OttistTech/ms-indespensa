package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}
