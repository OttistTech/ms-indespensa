package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.RecipeIngredient;
import com.ottistech.indespensa.api.ms_indespensa.model.User;

import java.util.List;
import java.util.stream.Collectors;

public record RecipeFullInfoResponseDTO(
        Long recipeId,
        Long createdBy,
        String title,
        String description,
        String level,
        Integer preparationTime,
        String preparationMethod,
        Boolean isShared,
        List<RecipeIngredientDetailsDTO> ingredients
) {

    public static RecipeFullInfoResponseDTO fromRecipeAndIngredients(User user, Recipe recipe, List<RecipeIngredient> ingredients) {
        List<RecipeIngredientDetailsDTO> ingredientDTOs = ingredients.stream()
                .map(ingredient -> new RecipeIngredientDetailsDTO(
                        ingredient.getIngredientFood().getFoodName(),
                        ingredient.getAmount(),
                        ingredient.getUnit(),
                        ingredient.getIsEssential()
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
                ingredientDTOs
        );
    }

}
