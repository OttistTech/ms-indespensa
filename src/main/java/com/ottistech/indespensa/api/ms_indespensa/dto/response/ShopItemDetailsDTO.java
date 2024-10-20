package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Provides detailed information about a specific shop item, including product details.")
public record ShopItemDetailsDTO(
        @Schema(description = "Unique identifier for the shop item", example = "123")
        Long itemId,

        @Schema(description = "ID of the user associated with the item", example = "1")
        Long userId,

        @Schema(description = "ID of the product associated with the item", example = "50")
        Long productId,

        @Schema(description = "URL of the product's image", example = "http://example.com/product-image.jpg")
        String productImageUrl,

        @Schema(description = "Food category of the product", example = "Fruit")
        String productFood,

        @Schema(description = "Name of the product", example = "Apple")
        String productName,

        @Schema(description = "Brand of the product", example = "Organic Farms")
        String productBrand,

        @Schema(description = "Description of the product", example = "Fresh organic apples")
        String productDescription,

        @Schema(description = "Amount in product measurement units", example = "1.50")
        BigDecimal productAmount,

        @Schema(description = "Unit of the product (e.g., kg, lb)", example = "kg")
        String productUnit,

        @Schema(description = "Amount of the product", example = "3")
        Integer amount
) {
}
