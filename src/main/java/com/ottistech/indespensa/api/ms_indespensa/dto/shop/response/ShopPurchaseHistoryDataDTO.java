package com.ottistech.indespensa.api.ms_indespensa.dto.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents detailed information about a product in a purchase history entry.")
public record ShopPurchaseHistoryDataDTO(
        @Schema(description = "Unique identifier for the product", example = "123")
        Long productId,

        @Schema(description = "Name of the product", example = "Apple")
        String productName,

        @Schema(description = "URL of the product's image", example = "https://example.com/image.jpg")
        String imageUrl,

        @Schema(description = "Amount of the product purchased", example = "2")
        Long amount
) {
}
