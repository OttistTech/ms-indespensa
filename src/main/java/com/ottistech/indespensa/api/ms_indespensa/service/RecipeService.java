package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreateRecipeDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Food;
import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.RecipeIngredient;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import com.ottistech.indespensa.api.ms_indespensa.repository.FoodRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.RecipeIngredientRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.RecipeRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final RecipeIngredientRepository ingredientRepository;

    @Transactional
    public RecipeFullInfoResponseDTO createRecipe(CreateRecipeDTO recipeDTO) {
        User user = userRepository.findById(recipeDTO.createdBy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        Recipe recipe = recipeRepository.save(new Recipe(
                user,
                recipeDTO.title(),
                recipeDTO.description(),
                recipeDTO.level(),
                recipeDTO.preparationTime(),
                recipeDTO.preparationMethod(),
                recipeDTO.isShared()
        ));

        List<RecipeIngredient> ingredients = recipeDTO.createRecipeIngredientList().stream()
                .map(dto -> {
                    Food food = foodRepository.findById(dto.foodId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food does not exist"));

                    return new RecipeIngredient(
                            recipe,
                            food,
                            dto.amount(),
                            dto.unit(),
                            dto.isEssential()
                    );
                })
                .collect(Collectors.toList());

        ingredientRepository.saveAll(ingredients);
        recipeRepository.save(recipe);

        return RecipeFullInfoResponseDTO.fromRecipeAndIngredients(user, recipe, ingredients);
    }
}
