package com.ottistech.indespensa.api.ms_indespensa.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product details retrieved from the name search.")
public record ProductSearchResponseDTO(

        @Schema(description = "The unique identifier of the product.", example = "1")
        Long productId,

        @Schema(description = "The unique identifier of the food associated with the product.", example = "10")
        Long foodId,

        @Schema(description = "The name of the product.", example = "Chocolate Bar")
        String productName,

        @Schema(description = "The name of the food category associated with the product.", example = "Snacks")
        String foodName,

        @Schema(description = "The URL of the product image.", example = "https://example.com/images/chocolate-bar.jpg")
        String imageUrl
) {
}
