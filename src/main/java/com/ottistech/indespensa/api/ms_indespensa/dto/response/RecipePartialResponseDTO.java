package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record RecipePartialResponseDTO(
        Long recipeId,
        String imageUrl,
        String title,
        String description,
        Integer amountIngredients,
        Integer amountInPantry,
        String level,
        Integer preparationTime,
        Integer numStars
) {}

