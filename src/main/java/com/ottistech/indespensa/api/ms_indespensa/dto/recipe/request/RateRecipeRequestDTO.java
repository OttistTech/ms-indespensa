package com.ottistech.indespensa.api.ms_indespensa.dto.recipe.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for rating a recipe.")
public record RateRecipeRequestDTO(

        @Schema(description = "The ID of the user rating the recipe", example = "1")
        @NotNull(message = "Field userId is required")
        @Min(value = 1, message = "Field userId must be at least 1")
        Long userId,

        @Schema(description = "The rating given to the recipe, from 0 to 5", example = "4")
        @NotNull(message = "Field numStars is required")
        @Min(value = 0, message = "Field numStars must be 0 => numStars <= 5")
        @Max(value = 5, message = "Field numStars must be 0 => numStars <= 5")
        Integer numStars
) {
}
