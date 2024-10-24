package com.ottistech.indespensa.api.ms_indespensa.dto.recipe_ingredient.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Data Transfer Object for creating a recipe ingredient")
public record CreateRecipeIngredientDTO(

        @Schema(description = "The ID of the food item", example = "123")
        @NotNull(message = "Field foodId is required")
        Long foodId,

        @Schema(description = "The amount of the ingredient needed for the recipe", example = "2.50", type = "number", format = "decimal")
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,

        @Schema(description = "The unit of measurement for the ingredient", example = "g")
        String unit,

        @Schema(description = "Indicates if the ingredient is essential", example = "true")
        @NotNull(message = "Field isEssential is required")
        Boolean isEssential
) {
}
