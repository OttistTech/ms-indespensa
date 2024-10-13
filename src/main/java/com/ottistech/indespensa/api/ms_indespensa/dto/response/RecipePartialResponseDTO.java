package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;

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
        BigDecimal numStars
) {
}