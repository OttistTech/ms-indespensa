package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b WHERE LOWER(b.brandName) = LOWER(:brandName)")
    Optional<Brand> findByBrandName(@Param("brandName") String brandName);
}
