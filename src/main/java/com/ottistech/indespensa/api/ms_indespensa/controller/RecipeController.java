package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.controller.contract.RecipeContract;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.request.CreateRecipeDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.request.RateRecipeRequestDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response.RecipeDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response.RecipeFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response.RecipePartialResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.RecipeService;
import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Availability;
import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Level;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
@AllArgsConstructor
public class RecipeController implements RecipeContract {

    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<RecipeFullInfoResponseDTO> createRecipe(
            @RequestBody
            @Valid
            CreateRecipeDTO recipeDTO
    ) {

        RecipeFullInfoResponseDTO recipe = recipeService.createRecipe(recipeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @GetMapping("/list")
    public Page<RecipePartialResponseDTO> listRecipes(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String pattern,
            @RequestParam(required = false, defaultValue = "") Level level,
            @RequestParam(required = false, defaultValue = "false") Boolean createdByYou,
            @RequestParam(required = false, defaultValue = "OUT_OF_PANTRY") Availability availability,
            @RequestParam(required = false, defaultValue = "0") Integer startPreparationTime,
            @RequestParam(required = false, defaultValue = "1440") Integer endPreparationTime
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return recipeService.getPaginatedRecipes(userId, pageable, pattern, level, createdByYou, availability, startPreparationTime, endPreparationTime);
    }

    @GetMapping("/{recipe_id}/details")
    public ResponseEntity<RecipeDetailsDTO> getRecipe(
            @PathVariable("recipe_id") Long recipeId,
            @RequestParam Long userId
    ) {

        RecipeDetailsDTO recipeDetail = recipeService.getRecipeDetails(userId, recipeId);

        return ResponseEntity.ok(recipeDetail);
    }

    @PostMapping("/{recipe_id}/rating")
    public ResponseEntity<Void> rateRecipe(
            @PathVariable("recipe_id")
            Long recipeId,

            @Valid
            @RequestBody
            RateRecipeRequestDTO rateRecipeRequestDTO
    ) {

        recipeService.rateRecipe(recipeId, rateRecipeRequestDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
