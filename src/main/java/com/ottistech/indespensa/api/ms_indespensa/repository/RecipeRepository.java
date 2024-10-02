package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("""
    SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO(
        r.recipeId,
        r.imageUrl,
        r.title,
        r.description,
        CAST(COUNT(DISTINCT ri.ingredientFood.foodId) as int),
        CAST(SUM(CASE
            WHEN pi.product.productId = p.productId AND p.foodId = ri.ingredientFood THEN 1
            ELSE 0
        END) as int),
        r.level,
        r.preparationTime,
        COALESCE(cr.numStars, 0)
    )
    FROM Recipe r
    LEFT JOIN r.ingredients ri
    LEFT JOIN Product p ON p.foodId = ri.ingredientFood
    LEFT JOIN PantryItem pi ON pi.product.productId = p.productId
        AND pi.user = :user
        AND pi.amount > 0
        AND pi.isActive = TRUE
    LEFT JOIN CompletedRecipe cr ON cr.recipe.recipeId = r.recipeId
        AND cr.user = :user
    WHERE r.isShared = TRUE
    GROUP BY r.recipeId, r.imageUrl, r.title, r.description, r.level, r.preparationTime, cr.numStars
    """)
    Page<RecipePartialResponseDTO> findRecipesWithIngredientsInPantryAndRating(
            @Param("user") User user,
            Pageable pageable
    );

}
