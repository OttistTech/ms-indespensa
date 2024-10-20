package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Represents a shop item response with basic information about the product and its amount.")
public record ShopItemResponseDTO(
        @Schema(description = "Unique identifier for the list item", example = "101")
        Long listItemId,

        @Schema(description = "ID of the user associated with the item", example = "1")
        Long userId,

        @Schema(description = "Name of the product", example = "Apple")
        String productName,

        @Schema(description = "URL of the product's image", example = "https://example.com/image.jpg")
        String imageUrl,

        @Schema(description = "Amount of the product", example = "3")
        Integer amount,

        @Schema(description = "Amount in product measurement units", example = "1.50")
        BigDecimal productAmount,

        @Schema(description = "Unit of the product (e.g., kg, lb)", example = "kg")
        String productUnit
) {
}
