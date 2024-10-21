package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopPurchaseHistoryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

// TODO: verify why Swagger is adding some Status Code that doesn't make sense in some endpoints

@Tag(name = "Shop Items", description = "Endpoints related to Shop Item management")
public interface ShopItemContract {

    @Operation(summary = "Get list of shop items for a user", description = "Retrieve the list of shop items for a specific user by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of shop items found",
                    content = @Content(schema = @Schema(implementation = ShopItemResponseDTO.class))),

            @ApiResponse(responseCode = "404", description = "No items found or user does not exist",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<List<ShopItemResponseDTO>> getShopItemListInfo(
            @Parameter(description = "User ID to retrieve shop items for", example = "1")
            Long userId
    );

    @Operation(summary = "Add a new shop item", description = "Add a new shop item for a specific user by his ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Shop item successfully added",
                    content = @Content(schema = @Schema(implementation = ShopItemResponseDTO.class), mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User or product not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<ShopItemResponseDTO> addShopItem(
            @Parameter(description = "User ID to add the shop item for", example = "1")
            Long userId,

            @Parameter(description = "Details of the shop item to be added")
            AddShopItemDTO shopItemDTO
    );

    @Operation(summary = "Get details of a specific shop item", description = "Retrieve detailed information of a specific shop item by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shop item details retrieved",
                    content = @Content(schema = @Schema(implementation = ShopItemDetailsDTO.class))),

            @ApiResponse(responseCode = "404", description = "Shop item not found")
    })
    ResponseEntity<ShopItemDetailsDTO> getShopItem(
            @Parameter(description = "Shop item ID", example = "123")
            Long shopItemId
    );

    @Operation(summary = "Update the amount of shop items", description = "Update the amount of multiple shop items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shop items successfully updated",
                    content = @Content(schema = @Schema(implementation = ShopItem.class)))
    })
    ResponseEntity<List<ShopItem>> updateShopItemsAmount(
            @Parameter(description = "List of shop items with updated amounts")
            List<UpdateProductItemAmountDTO> shopItems
    );

    @Operation(summary = "Get purchase history of a user", description = "Retrieve the purchase history of a specific user by his ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchase history retrieved",
                    content = @Content(schema = @Schema(implementation = ShopPurchaseHistoryItemDTO.class))),

            @ApiResponse(responseCode = "404", description = "User or purchase history not found")
    })
    ResponseEntity<List<ShopPurchaseHistoryItemDTO>> getPurchaseHistoryInfo(
            @Parameter(description = "User ID", example = "123")
            Long userId
    );
}
