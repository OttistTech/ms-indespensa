package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request.AddPantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request.CreatePantryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemPartialDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response.PantryItemsNextToValidityDate;
import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Pantry Items", description = "Endpoints related to Pantry Item management")
public interface PantryItemContract {

    @Operation(summary = "Create a pantry item", description = "Creates a new pantry item for a specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the pantry item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PantryItemResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PantryItemResponseDTO> createPantryItem(
            @Parameter(in = ParameterIn.PATH, description = "The ID of the user for whom the pantry item is being created", required = true, example = "1")
            Long userId,

            @Parameter(description = "Details of the pantry item to be created", required = true)
            @RequestBody
            @Valid
            CreatePantryItemDTO pantryItem
    );

    @Operation(summary = "List active pantry items for a user", description = "Retrieves a list of active pantry items associated with a specific user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of active pantry items",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<List<PantryItemPartialDTO>> listPantryItems(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user whose pantry items are being retrieved", required = true, example = "1")
            Long userId
    );

    @Operation(summary = "Update the amounts of pantry items", description = "Updates the specified amounts for the provided pantry items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the pantry items",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PantryItem.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "Pantry items not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Void> updatePantryItemsAmount(
            @Parameter(description = "List of pantry items with updated amounts", required = true)
            @RequestBody
            @Valid
            List<UpdateProductItemAmountDTO> pantryItems
    );

    @Operation(summary = "Retrieve details of a specific pantry item", description = "Fetches detailed information about a pantry item using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pantry item details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PantryItemDetailsDTO.class))),

            @ApiResponse(responseCode = "404", description = "Pantry item not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PantryItemDetailsDTO> getPantryItem(
            @Parameter(in = ParameterIn.PATH, description = "The ID of the pantry item to retrieve details for", required = true, example = "1")
            Long pantryItemId
    );

    @Operation(summary = "Add all shop items to the pantry", description = "Moves all items from the user's shopping list to the pantry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully added all items to the pantry",
                    content = @Content),

            @ApiResponse(responseCode = "400", description = "Invalid user ID provided",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User not found or doesn't have any shop items in his shopping list",
                    content = @Content(mediaType = "application/json")),
    })
    ResponseEntity<Void> addAllShopItemsToPantry(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user for whom the items will be added to the pantry", required = true, example = "1")
            Long userId
    );

    @Operation(summary = "Add a pantry item", description = "Adds a new pantry item for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added the pantry item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PantryItemResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PantryItemResponseDTO> addPantryItem(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user for whom the pantry item is being added", required = true, example = "1")
            Long userId,

            @Parameter(description = "Details of the pantry item to be added", required = true)
            @RequestBody
            @Valid
            AddPantryItemDTO pantryItemDTO
    );

    @Operation(summary = "Find pantry items nearing their expiration date", description = "Retrieves a list of pantry items that are close to their expiration date for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of soon-to-expire pantry items",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PantryItemsNextToValidityDate.class))),

            @ApiResponse(responseCode = "404", description = "User not found or no pantry items next to validity date",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<List<PantryItemsNextToValidityDate>> findExpiringPantryItemsByUser(
            @Parameter(description = "ID of the user whose pantry items are being retrieved", required = true, example = "1")
            Long userId
    );
}
