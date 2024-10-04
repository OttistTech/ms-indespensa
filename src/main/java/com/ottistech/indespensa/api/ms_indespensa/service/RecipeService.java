package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreateRecipeDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.RateRecipeRequestDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeIngredientDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.*;
import com.ottistech.indespensa.api.ms_indespensa.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final RecipeIngredientRepository ingredientRepository;
    private final CompletedRecipeRepository completedRecipeRepository;

    @Transactional
    public RecipeFullInfoResponseDTO createRecipe(CreateRecipeDTO recipeDTO) {
        User user = userRepository.findById(recipeDTO.createdBy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));


        // TODO: put a max of preparationTime
        Recipe recipe = new Recipe(
                user,
                recipeDTO.title(),
                recipeDTO.description(),
                recipeDTO.level(),
                recipeDTO.preparationTime(),
                recipeDTO.preparationMethod(),
                recipeDTO.isShared(),
                recipeDTO.imageUrl()
        );

        recipeRepository.save(recipe);

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

        return RecipeFullInfoResponseDTO.fromRecipeAndIngredients(user, recipe, ingredients);
    }

    public Page<RecipePartialResponseDTO> getPaginatedRecipes(
            Long userId,
            Pageable pageable,
            String difficulty,
            String availability,
            Integer startPreparationTime,
            Integer endPreparationTime
    ) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        difficulty = (difficulty == null) ? "" : difficulty;
        startPreparationTime = (startPreparationTime == null) ? 0 : startPreparationTime;
        endPreparationTime = (endPreparationTime == null) ? 1440 : endPreparationTime;

        Page<RecipePartialResponseDTO> responseDTOPage;

        if (availability == null) {
            responseDTOPage = recipeRepository.findRecipesWithIngredientsInOrNotInPantryAndRating(user, pageable, difficulty, startPreparationTime, endPreparationTime);
        } else {
            responseDTOPage = recipeRepository.findRecipesWithIngredientsInPantryAndRating(user, pageable, difficulty, startPreparationTime, endPreparationTime);
        }

        if (responseDTOPage.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No recipes found with this filters");

        return responseDTOPage;
    }

    public RecipeDetailsDTO getRecipeDetails(Long userId, Long recipeId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist")
        );

        RecipePartialResponseDTO recipe = recipeRepository.findRecipeWithDetailsById(user, recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        List<RecipeIngredientDetailsDTO> ingredientDetails = ingredientRepository.findIngredientsByRecipeId(recipeId, user);

        return recipe.toRecipeDetailsDTO(ingredientDetails);
    }

    @Transactional
    public void rateRecipe(Long recipeId, RateRecipeRequestDTO rateRecipeRequestDTO) {
        User user = userRepository.findById(rateRecipeRequestDTO.userId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist")
        );

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found")
        );

        Optional<CompletedRecipe> existingRatingOpt = completedRecipeRepository.findByUserAndRecipe(user, recipe);

        if (existingRatingOpt.isPresent()) {
            CompletedRecipe existingRating = existingRatingOpt.get();

            existingRating.setNumStars(rateRecipeRequestDTO.numStars());
            completedRecipeRepository.save(existingRating);
        } else {
            CompletedRecipe newRating = new CompletedRecipe(user, recipe, rateRecipeRequestDTO.numStars());

            completedRecipeRepository.save(newRating);
        }
    }
}
