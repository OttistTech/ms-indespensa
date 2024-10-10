package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RecipePartialResponseDTO(
        Long recipeId,
        String imageUrl,
        String title,
        String description,
        Integer amountIngredients,
        Integer amountInPantry,
        String difficulty,
        Integer preparationTime,
        String preparationMethod,
        BigDecimal numStars
) {

    public RecipeDetailsDTO toRecipeDetailsDTO(List<RecipeIngredientDetailsDTO> ingredientDetails) {
        return new RecipeDetailsDTO(
                recipeId,
                imageUrl,
                difficulty,
                title,
                numStars,
                description,
                amountIngredients,
                amountInPantry,
                preparationTime,
                preparationMethod,
                ingredientDetails
        );
    }
}

