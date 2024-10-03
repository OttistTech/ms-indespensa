package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.util.List;

public record RecipeDetailsDTO(
        Long recipeId,
        String imageUrl,
        String level,
        String title,
        Integer numStars,
        String description,
        Integer amountIngredients,
        Integer amountInPantry,
        Integer preparationTime,
        String preparationMethod,
        List<RecipeIngredientDetailsDTO> ingredients
) {
}
