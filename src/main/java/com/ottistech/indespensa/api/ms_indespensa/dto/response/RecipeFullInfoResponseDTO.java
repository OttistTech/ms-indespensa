package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.RecipeIngredient;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Difficulty;

import java.util.List;
import java.util.stream.Collectors;

public record RecipeFullInfoResponseDTO(
        Long recipeId,
        Long createdBy,
        String title,
        String description,
        String difficulty,
        Integer preparationTime,
        String preparationMethod,
        Boolean isShared,
        String imageUrl,
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
                recipe.getDifficulty(),
                recipe.getPreparationTime(),
                recipe.getPreparationMethod(),
                recipe.getIsShared(),
                recipe.getImageUrl(),
                ingredientDTOs
        );
    }

}
