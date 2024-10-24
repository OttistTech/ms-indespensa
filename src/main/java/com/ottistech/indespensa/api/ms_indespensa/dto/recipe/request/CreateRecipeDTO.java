package com.ottistech.indespensa.api.ms_indespensa.dto.recipe.request;

import com.ottistech.indespensa.api.ms_indespensa.dto.recipe_ingredient.request.CreateRecipeIngredientDTO;
import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "DTO for creating a new recipe.")
public record CreateRecipeDTO(

        @Schema(description = "The ID of the user creating the recipe", example = "201")
        @NotNull(message = "Field createdBy is required")
        Long createdBy,

        @Schema(description = "The title of the recipe", example = "Pasta Primavera")
        @NotNull(message = "Field title is required")
        String title,

        @Schema(description = "A brief description of the recipe", example = "A colorful pasta dish with seasonal vegetables.")
        @NotNull(message = "Field description is required")
        String description,

        @Schema(description = "The difficulty level of the recipe (e.g., EASY, MEDIUM, HARD)", example = "EASY")
        @NotNull(message = "Field level is required")
        Level level,

        @Schema(description = "The total preparation time in minutes", example = "25")
        @NotNull(message = "Field preparationTime is required")
        Integer preparationTime,

        @Schema(description = "A brief summary of the preparation method", example = "Boil pasta, sauté vegetables, mix and serve.")
        @NotNull(message = "Field preparationMethod is required")
        String preparationMethod,

        @Schema(description = "Indicates if the recipe can be shared with other users", example = "true")
        @NotNull(message = "Field isShared is required")
        Boolean isShared,

        @Schema(description = "The URL of the recipe's image", example = "https://example.com/images/pasta_primavera.jpg")
        String imageUrl,

        @Schema(description = "List of ingredients required for the recipe")
        @NotNull(message = "The recipe must have at least one ingredient")
        List<CreateRecipeIngredientDTO> createRecipeIngredientList

) {
}
