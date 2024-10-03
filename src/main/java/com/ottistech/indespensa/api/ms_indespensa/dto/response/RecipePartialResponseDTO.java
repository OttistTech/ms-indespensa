package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.util.List;

public record RecipePartialResponseDTO(
        Long recipeId,
        String imageUrl,
        String title,
        String description,
        Integer amountIngredients,
        Integer amountInPantry,
        String level,
        Integer preparationTime,
        String preparationMethod,
        Integer numStars
) {

    public RecipeDetailsDTO toRecipeDetailsDTO(List<RecipeIngredientDetailsDTO> ingredientDetails) {
        return new RecipeDetailsDTO(
                recipeId,
                imageUrl,
                level,
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

