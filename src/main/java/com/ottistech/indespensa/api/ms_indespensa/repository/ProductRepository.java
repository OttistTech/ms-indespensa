package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.product.response.ProductSearchResponseDTO;
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

    @Query("""
    SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.product.response.ProductSearchResponseDTO(
        p.productId,
        f.foodId,
        p.name,
        f.foodName,
        p.imageUrl
    )
    FROM Product p
    JOIN p.foodId f
    WHERE LOWER(p.name) LIKE LOWER(CONCAT(:pattern, '%'))
    """)
    List<ProductSearchResponseDTO> findAllByNameStartingWithIgnoreCase(String pattern);
}
