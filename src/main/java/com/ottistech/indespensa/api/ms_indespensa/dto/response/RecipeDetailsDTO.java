package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RecipeDetailsDTO(
        Long recipeId,
        String imageUrl,
        String level,
        String title,
        BigDecimal numStars,
        String description,
        Integer amountIngredients,
        Integer amountInPantry,
        Integer preparationTime,
        String preparationMethod,
        List<RecipeIngredientDetailsDTO> ingredients
) {
    public static RecipeDetailsDTO fromPartialResponseDTO(
            RecipePartialResponseDTO recipePartialResponseDTO,
            List<RecipeIngredientDetailsDTO> ingredientDetails) {

        return new RecipeDetailsDTO(
                recipePartialResponseDTO.recipeId(),
                recipePartialResponseDTO.imageUrl(),
                recipePartialResponseDTO.level(),
                recipePartialResponseDTO.title(),
                recipePartialResponseDTO.numStars(),
                recipePartialResponseDTO.description(),
                recipePartialResponseDTO.amountIngredients(),
                recipePartialResponseDTO.amountInPantry(),
                recipePartialResponseDTO.preparationTime(),
                recipePartialResponseDTO.preparationMethod(),
                ingredientDetails
        );
    }
}