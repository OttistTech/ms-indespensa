package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.request.CreateRecipeDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.request.RateRecipeRequestDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response.RecipeDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response.RecipeFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.recipe.response.RecipePartialResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Availability;
import com.ottistech.indespensa.api.ms_indespensa.utils.enums.Level;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recipes", description = "Endpoints related to Recipe management")
public interface RecipeContract {

    @Operation(summary = "Create a new recipe", description = "Creates a new recipe and returns the full recipe information.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recipe created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeFullInfoResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User of Food not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<RecipeFullInfoResponseDTO> createRecipe(
            @Parameter(description = "Details of recipe to be added", required = true)
            CreateRecipeDTO recipeDTO
    );

    @Operation(summary = "List Recipes", description = "Retrieve a paginated list of recipes based on filters like pattern, level, availability, and preparation time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of recipes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipePartialResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "No recipes found for the given filters",
                    content = @Content(mediaType = "application/json"))
    })
    Page<RecipePartialResponseDTO> listRecipes(
            @Parameter(in = ParameterIn.QUERY, description = "The ID of the user requesting the recipes", required = true, example = "101")
            Long userId,

            @Parameter(in = ParameterIn.QUERY, description = "Page number to retrieve (0-indexed)", example = "0")
            int page,

            @Parameter(in = ParameterIn.QUERY, description = "Number of items per page", example = "10")
            int size,

            @Parameter(in = ParameterIn.QUERY, description = "Search pattern for filtering recipes by name", example = "Spaghetti")
            String pattern,

            @Parameter(in = ParameterIn.QUERY, description = "Filter by difficulty level (e.g., EASY, MEDIUM, HARD)", schema = @Schema(implementation = Level.class), example = "EASY")
            Level level,

            @Parameter(in = ParameterIn.QUERY, description = "Filter recipes created by the current user", example = "true")
            Boolean createdByYou,

            @Parameter(in = ParameterIn.QUERY, description = "Filter by availability status (e.g., IN_PANTRY, OUT_OF_PANTRY)", schema = @Schema(implementation = Availability.class), example = "OUT_OF_PANTRY")
            Availability availability,

            @Parameter(in = ParameterIn.QUERY, description = "Filter by minimum preparation time in minutes", example = "0")
            Integer startPreparationTime,

            @Parameter(in = ParameterIn.QUERY, description = "Filter by maximum preparation time in minutes", example = "1440")
            Integer endPreparationTime
    );

    @Operation(summary = "Get Recipe Details", description = "Retrieve detailed information about a specific recipe using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recipe details",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDetailsDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid user ID or recipe ID",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "Recipe not found for the given ID",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<RecipeDetailsDTO> getRecipe(
            @Parameter(in = ParameterIn.PATH, description = "The ID of the recipe to retrieve", required = true, example = "101")
            Long recipeId,

            @Parameter(in = ParameterIn.QUERY, description = "The ID of the user requesting the recipe details", required = true, example = "201")
            Long userId
    );

    @Operation(summary = "Rate a recipe", description = "Allows a user to rate a specific recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully rated the recipe"),

            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User or Recipe not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Void> rateRecipe(
            @Parameter(in = ParameterIn.PATH, description = "The ID of the recipe to rate", required = true, example = "1001")
            Long recipeId,

            @Parameter(description = "The rating details for the recipe", required = true)
            RateRecipeRequestDTO rateRecipeRequestDTO
    );

}
