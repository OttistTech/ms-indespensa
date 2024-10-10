package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RecipeDetailsDTO(
        Long recipeId,
        String imageUrl,
        String difficulty,
        String title,
        BigDecimal numStars,
        String description,
        Integer amountIngredients,
        Integer amountInPantry,
        Integer preparationTime,
        String preparationMethod,
        List<RecipeIngredientDetailsDTO> ingredients
) {
}
