package com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Partial response DTO representing summary information about a recipe.")
public record RecipePartialResponseDTO(

        @Schema(description = "The unique ID of the recipe", example = "1001")
        Long recipeId,

        @Schema(description = "The URL of the recipe's image", example = "https://example.com/images/recipe.jpg")
        String imageUrl,

        @Schema(description = "The title of the recipe", example = "Spaghetti Bolognese")
        String title,

        @Schema(description = "A brief description of the recipe", example = "A classic Italian dish with a rich meat sauce.")
        String description,

        @Schema(description = "The total number of ingredients required for the recipe", example = "5")
        Integer amountIngredients,

        @Schema(description = "The number of ingredients that the user already has in their pantry", example = "3")
        Integer amountInPantry,

        @Schema(description = "The difficulty level of the recipe (e.g., EASY, MEDIUM, HARD)", example = "EASY")
        String level,

        @Schema(description = "The total preparation time in minutes", example = "30")
        Integer preparationTime,

        @Schema(description = "A brief summary of the preparation method", example = "Boil pasta, cook meat sauce, mix and serve.")
        String preparationMethod,

        @Schema(description = "The average rating of the recipe, represented as stars", example = "4.5")
        BigDecimal numStars
) {
}