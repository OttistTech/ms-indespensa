package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Difficulty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRecipeDTO(

        @NotNull(message = "Field createdBy is required") Long createdBy,
        @NotNull(message = "Field title is required") String title,
        @NotNull(message = "Field description is required") String description,
        @NotNull(message = "Field difficulty is required") Difficulty difficulty,
        @NotNull(message = "Field preparationTime is required") Integer preparationTime,
        @NotNull(message = "Field preparationMethod is required") String preparationMethod,
        @NotNull(message = "Field isShared is required") Boolean isShared,
        String imageUrl,
        @NotNull(message = "The recipe must have at least one ingredient") List<CreateRecipeIngredientDTO> createRecipeIngredientList

) {
}
