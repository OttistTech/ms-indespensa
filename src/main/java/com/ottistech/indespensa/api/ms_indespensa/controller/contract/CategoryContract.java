package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Category", description = "Endpoints related to Category management")
public interface CategoryContract {

    @Operation(summary = "List Categories", description = "Retrieves a list of categories, filtered by an optional pattern.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of categories.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input pattern.",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "No categories matching the pattern",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<List<String>> listCategories(
            @Parameter(in = ParameterIn.QUERY, description = "An optional pattern to filter categories", example = "snack")
            String pattern
    );
}
