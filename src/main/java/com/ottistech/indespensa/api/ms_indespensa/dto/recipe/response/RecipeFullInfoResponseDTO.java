package com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response;

import com.ottistech.indespensa.api.ms_indespensa.dto.recipe_ingredient.response.RecipeIngredientDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.RecipeIngredient;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Detailed response DTO representing a recipe with all its attributes.")
public record RecipeFullInfoResponseDTO(

        @Schema(description = "The unique ID of the recipe", example = "1001")
        Long recipeId,

        @Schema(description = "The ID of the user who created the recipe", example = "201")
        Long createdBy,

        @Schema(description = "The title of the recipe", example = "Vegetable Stir Fry")
        String title,

        @Schema(description = "A brief description of the recipe", example = "A quick and healthy vegetable stir fry.")
        String description,

        @Schema(description = "The difficulty level of the recipe (e.g., EASY, MEDIUM, HARD)", example = "EASY")
        String level,

        @Schema(description = "The total preparation time in minutes", example = "15")
        Integer preparationTime,

        @Schema(description = "A brief summary of the preparation method", example = "Stir fry vegetables in a pan and serve hot.")
        String preparationMethod,

        @Schema(description = "Indicates if the recipe can be shared with other users", example = "true")
        Boolean isShared,

        @Schema(description = "The URL of the recipe's image", example = "https://example.com/images/vegetable_stir_fry.jpg")
        String imageUrl,

        @Schema(description = "List of ingredients with their details used in the recipe")
        List<RecipeIngredientDetailsDTO> ingredients
) {

    public static RecipeFullInfoResponseDTO fromRecipeAndIngredients(User user, Recipe recipe, List<RecipeIngredient> ingredients) {
        List<RecipeIngredientDetailsDTO> ingredientDTOs = ingredients.stream()
                .map(ingredient -> new RecipeIngredientDetailsDTO(
                        ingredient.getIngredientFood().getFoodId(),
                        ingredient.getIngredientFood().getFoodName(),
                        ingredient.getAmount(),
                        ingredient.getUnit(),
                        ingredient.getIsEssential(),
                        null
                ))
                .collect(Collectors.toList());

        return new RecipeFullInfoResponseDTO(
                recipe.getRecipeId(),
                user.getUserId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getLevel(),
                recipe.getPreparationTime(),
                recipe.getPreparationMethod(),
                recipe.getIsShared(),
                recipe.getImageUrl(),
                ingredientDTOs
        );
    }

}
