package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE LOWER(f.foodName) = LOWER(:foodName)")
    Optional<Food> findByFoodName(
            @Param("foodName")
            String alias
    );

}
