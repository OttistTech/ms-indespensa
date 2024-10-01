package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreateRecipeDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.RecipeFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.RecipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
