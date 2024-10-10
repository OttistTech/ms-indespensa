package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("""
    SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO(
        r.recipeId,
        r.imageUrl,
        r.title,
        r.description,
        CAST(COUNT(DISTINCT ri.ingredientFood.foodId) as int),
        CAST(COUNT(DISTINCT pi.product.productId) as int),
        r.difficulty,
        r.preparationTime,
        r.preparationMethod,
        CAST(ROUND(COALESCE(AVG(cr.numStars), 0), 2) AS bigdecimal)
    )
    FROM Recipe r
    LEFT JOIN r.ingredients ri
    LEFT JOIN Product p ON p.foodId = ri.ingredientFood
    LEFT JOIN PantryItem pi ON pi.product.productId = p.productId
        AND pi.user = :user
        AND pi.amount > 0
        AND pi.isActive = TRUE
    LEFT JOIN CompletedRecipe cr ON cr.recipe.recipeId = r.recipeId
    WHERE
        r.isShared = TRUE AND
        LOWER(r.difficulty) LIKE CONCAT('%', LOWER(:difficulty), '%') AND
        r.preparationTime BETWEEN :startPreparationTime AND :endPreparationTime
    GROUP BY r.recipeId, r.imageUrl, r.title, r.description, r.difficulty, r.preparationTime
    ORDER BY 6 DESC
    """)
    Page<RecipePartialResponseDTO> findRecipesWithIngredientsInOrNotInPantryAndRating(
            @Param("user") User user,
            Pageable pageable,
            String difficulty,
            Integer startPreparationTime,
            Integer endPreparationTime
    );

    @Query("""
    SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO(
        r.recipeId,
        r.imageUrl,
        r.title,
        r.description,
        CAST(COUNT(DISTINCT ri.ingredientFood.foodId) as int),
        CAST(COUNT(DISTINCT pi.product.productId) as int),
        r.difficulty,
        r.preparationTime,
        r.preparationMethod,
        CAST(ROUND(COALESCE(AVG(cr.numStars), 0), 2) AS bigdecimal)
    )
    FROM Recipe r
    LEFT JOIN r.ingredients ri
    LEFT JOIN Product p ON p.foodId = ri.ingredientFood
    LEFT JOIN PantryItem pi ON pi.product.productId = p.productId
        AND pi.user = :user
        AND pi.amount > 0
        AND pi.isActive = TRUE
    LEFT JOIN CompletedRecipe cr ON cr.recipe.recipeId = r.recipeId
    WHERE
        r.isShared = TRUE AND
        LOWER(r.difficulty) LIKE CONCAT('%', LOWER(:difficulty), '%') AND
        r.preparationTime BETWEEN :startPreparationTime AND :endPreparationTime
    GROUP BY r.recipeId, r.imageUrl, r.title, r.description, r.difficulty, r.preparationTime
    HAVING CAST(COUNT(DISTINCT ri.ingredientFood.foodId) as int) = CAST(COUNT(DISTINCT pi.product.productId) as int)
    ORDER BY 6 DESC
    """)
    Page<RecipePartialResponseDTO> findRecipesWithIngredientsInPantryAndRating(
            @Param("user") User user,
            Pageable pageable,
            String difficulty,
            Integer startPreparationTime,
            Integer endPreparationTime
    );

    @Query("""
    SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO(
        r.recipeId,
        r.imageUrl,
        r.title,
        r.description,
        CAST(COUNT(DISTINCT ri.ingredientFood.foodId) as int),
        CAST(COUNT(DISTINCT pi.product.productId) as int),
        r.difficulty,
        r.preparationTime,
        r.preparationMethod,
        CAST(ROUND(COALESCE(AVG(cr.numStars), 0), 2) AS bigdecimal)
    )
    FROM Recipe r
    LEFT JOIN r.ingredients ri
    LEFT JOIN Product p ON p.foodId = ri.ingredientFood
    LEFT JOIN PantryItem pi ON pi.product.productId = p.productId
        AND pi.amount > 0
        AND pi.isActive = TRUE
        AND pi.user = :user
    LEFT JOIN CompletedRecipe cr ON cr.recipe.recipeId = r.recipeId
    WHERE r.isShared = TRUE AND r.recipeId = :recipeId
    GROUP BY r.recipeId, r.imageUrl, r.title, r.description, r.difficulty, r.preparationTime
    """)
    Optional<RecipePartialResponseDTO> findRecipeWithDetailsById(
            @Param("user") User user,
            @Param("recipeId") Long recipeId
    );

}
