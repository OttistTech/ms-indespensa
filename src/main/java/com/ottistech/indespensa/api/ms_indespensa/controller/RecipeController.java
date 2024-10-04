package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreateRecipeDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.RateRecipeRequestDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipePartialResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.RecipeService;
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
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<RecipeFullInfoResponseDTO> createRecipe(@RequestBody @Valid CreateRecipeDTO recipeDTO) {

        RecipeFullInfoResponseDTO recipe = recipeService.createRecipe(recipeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RecipePartialResponseDTO>> listRecipes(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String difficulty,
            @RequestParam(required = false, defaultValue = "") String availability,
            @RequestParam(required = false) Integer startPreparationTime,
            @RequestParam(required = false) Integer endPreparationTime
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RecipePartialResponseDTO> recipePage = recipeService.getPaginatedRecipes(userId, pageable, difficulty, availability, startPreparationTime, endPreparationTime);

        return ResponseEntity.ok(recipePage);
    }

    @GetMapping("/{recipe_id}/details")
    public ResponseEntity<RecipeDetailsDTO> getRecipe(
            @PathVariable("recipe_id") Long recipeId,
            @RequestParam Long userId) {

        RecipeDetailsDTO recipeDetail = recipeService.getRecipeDetails(userId, recipeId);

        return ResponseEntity.ok(recipeDetail);
    }

    @PostMapping("/{recipe_id}/rating")
    public ResponseEntity<Void> rateRecipe(
            @PathVariable("recipe_id") Long recipeId,
            @Valid @RequestBody RateRecipeRequestDTO rateRecipeRequestDTO) {

        recipeService.rateRecipe(recipeId, rateRecipeRequestDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
