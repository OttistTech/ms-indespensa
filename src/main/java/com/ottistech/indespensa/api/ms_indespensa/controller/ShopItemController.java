package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateProductItemAmountDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemDetailsDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopItemResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ShopPurchaseHistoryItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.ShopItem;
import com.ottistech.indespensa.api.ms_indespensa.service.ShopItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/shop")
@Tag(name = "Shop Items", description = "Endpoints related to Shop Item management")
public class ShopItemController {

    private final ShopItemService shopItemService;

    // TODO: verify why Swagger is adding some Status Code that doesn't make sense in some endpoints

    @GetMapping("/{user_id}/list")
    @Operation(summary = "Get list of shop items for a user", description = "Retrieve the list of shop items for a specific user by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of shop items found",
                    content = @Content(schema = @Schema(implementation = ShopItemResponseDTO.class))),

            @ApiResponse(responseCode = "404", description = "No items found or user does not exist",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<ShopItemResponseDTO>> getShopItemListInfo(
            @Parameter(description = "User ID to retrieve shop items for", example = "1")
            @PathVariable("user_id") Long userId
    ) {

        List<ShopItemResponseDTO> listItemResponse = shopItemService.getListItem(userId);

        return ResponseEntity.ok(listItemResponse);
    }

    @PostMapping("/{user_id}/add")
    @Operation(summary = "Add a new shop item", description = "Add a new shop item for a specific user by his ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Shop item successfully added",
                    content = @Content(schema = @Schema(implementation = ShopItemResponseDTO.class), mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User or product not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ShopItemResponseDTO> addShopItem(
            @Parameter(description = "User ID to add the shop item for", example = "1")
            @PathVariable("user_id") Long userId,

            @Parameter(description = "Details of the shop item to be added")
            @Valid @RequestBody AddShopItemDTO shopItemDTO) {

        ShopItemResponseDTO itemResponseDTO = shopItemService.addShopItem(userId, shopItemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponseDTO);
    }

    @GetMapping("/{shop_item_id}/details")
    @Operation(summary = "Get details of a specific shop item", description = "Retrieve detailed information of a specific shop item by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shop item details retrieved",
                    content = @Content(schema = @Schema(implementation = ShopItemDetailsDTO.class))),

            @ApiResponse(responseCode = "404", description = "Shop item not found")
    })
    public ResponseEntity<ShopItemDetailsDTO> getShopItem(
            @Parameter(description = "Shop item ID", example = "123")
            @PathVariable("shop_item_id") Long shopItemId
    ) {

        ShopItemDetailsDTO shopItem = shopItemService.getShopItemDetails(shopItemId);

        return ResponseEntity.ok(shopItem);
    }

    @PatchMapping("/update-items-amount")
    @Operation(summary = "Update the amount of shop items", description = "Update the amount of multiple shop items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shop items successfully updated",
                    content = @Content(schema = @Schema(implementation = ShopItem.class)))
    })
    public ResponseEntity<List<ShopItem>> updateShopItemsAmount(
            @Parameter(description = "List of shop items with updated amounts")
            @RequestBody @Valid List<UpdateProductItemAmountDTO> shopItems
    ) {

        List<ShopItem> updatedItems = shopItemService.updateShopItemsAmount(shopItems);

        return ResponseEntity.ok(updatedItems);
    }

    @GetMapping("/{user_id}/list/history")
    @Operation(summary = "Get purchase history of a user", description = "Retrieve the purchase history of a specific user by his ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchase history retrieved",
                    content = @Content(schema = @Schema(implementation = ShopPurchaseHistoryItemDTO.class))),

            @ApiResponse(responseCode = "404", description = "User or purchase history not found")
    })
    public ResponseEntity<List<ShopPurchaseHistoryItemDTO>> getPurchaseHistoryInfo(
            @PathVariable("user_id") Long userId
    ) {

        List<ShopPurchaseHistoryItemDTO> historyItems = shopItemService.getPurchaseHistoryItems(userId);
        return ResponseEntity.ok(historyItems);
    }
}
