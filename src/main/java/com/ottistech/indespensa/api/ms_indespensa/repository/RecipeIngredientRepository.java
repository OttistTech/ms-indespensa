package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeIngredientDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.RecipeIngredient;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    @Query("""
    SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeIngredientDetailsDTO(
        ri.ingredientFood.foodId,
        ri.ingredientFood.foodName,
        ri.amount,
        ri.unit,
        ri.isEssential,
        CASE
            WHEN COUNT(DISTINCT pi) > 0 THEN TRUE
            ELSE FALSE
        END
    )
    FROM Recipe r
    JOIN r.ingredients ri
    LEFT JOIN Product p ON p.foodId = ri.ingredientFood
    LEFT JOIN PantryItem pi ON pi.product.productId = p.productId
        AND pi.user = :user
        AND pi.amount > 0
        AND pi.isActive = TRUE
    WHERE r.recipeId = :recipeId
    GROUP BY ri.ingredientFood.foodId, ri.ingredientFood.foodName, ri.amount, ri.unit, ri.isEssential
    """)
    List<RecipeIngredientDetailsDTO> findIngredientsByRecipeId(@Param("recipeId") Long recipeId, @Param("user") User user);


}
