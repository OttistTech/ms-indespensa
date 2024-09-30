package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.eanCode = :eanCode AND p.eanCode IS NOT NULL")
    Optional<Product> findByEanCodeNotNull(@Param("eanCode") String eanCode);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:name)")
    Optional<Product> findByNameNotNull(@Param("name") String name);

    List<Product> findAllByNameStartingWithIgnoreCase(String pattern);
}
