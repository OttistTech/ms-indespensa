package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO of details about a pantry item.")
public record PantryItemDetailsDTO(

        @Schema(description = "Unique identifier for the pantry item", example = "1001")
        Long itemId,

        @Schema(description = "ID of the user who owns this pantry item", example = "2001")
        Long userId,

        @Schema(description = "ID of the associated product", example = "3001")
        Long productId,

        @Schema(description = "URL of the product's image", example = "https://example.com/images/product1.jpg")
        String productImageUrl,

        @Schema(description = "Food category or type of the product", example = "Cereal")
        String productFood,

        @Schema(description = "Name of the product", example = "Cornflakes")
        String productName,

        @Schema(description = "Brand of the product", example = "Kellogg's")
        String productBrand,

        @Schema(description = "Description of the product", example = "Crunchy cornflakes cereal, 500g")
        String productDescription,

        @Schema(description = "Amount of the product", example = "500")
        BigDecimal productAmount,

        @Schema(description = "Unit of measure for the product amount", example = "g")
        String productUnit,

        @Schema(description = "Quantity of the product in the pantry", example = "3")
        Integer amount,

        @Schema(description = "Expiration date of the pantry item", example = "2024-12-31")
        LocalDate validityDate,

        @Schema(description = "Flag indicating if product was already used by user", example = "false")
        Boolean wasOpened
) {
}
