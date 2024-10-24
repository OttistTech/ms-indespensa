package com.ottistech.indespensa.api.ms_indespensa.dto.recipe_ingredient.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Details about an ingredient used in a recipe.")
public record RecipeIngredientDetailsDTO(

        @Schema(description = "The unique ID of the food item", example = "2001")
        Long foodId,

        @Schema(description = "The name of the food item", example = "Olive Oil")
        String foodName,

        @Schema(description = "The amount of the ingredient", example = "2.5")
        BigDecimal amount,

        @Schema(description = "The unit of measurement for the amount (e.g., g, L)", example = "g")
        String unit,

        @Schema(description = "Indicates if the ingredient is essential for the recipe", example = "true")
        Boolean isEssential,

        @Schema(description = "Indicates if the ingredient is available in the user's pantry", example = "false")
        Boolean isInPantry
) {
}
