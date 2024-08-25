package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByFoodName(String alias);
}
