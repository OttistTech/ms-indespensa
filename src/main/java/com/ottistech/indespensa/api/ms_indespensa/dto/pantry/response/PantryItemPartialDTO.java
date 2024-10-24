package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO representing a partial view of a pantry item")
public record PantryItemPartialDTO(

        @Schema(description = "ID of the user who owns the pantry item", example = "1")
        Long userId,

        @Schema(description = "ID of the pantry item", example = "1001")
        Long pantryItemId,

        @Schema(description = "Name of the product", example = "Olive Oil")
        String name,

        @Schema(description = "URL of the product image", example = "https://example.com/images/olive_oil.jpg")
        String imageUrl,

        @Schema(description = "Amount of the product", example = "500.00")
        BigDecimal productAmount,

        @Schema(description = "Unit of the product amount", example = "ml")
        String productUnit,

        @Schema(description = "Amount of this product in the pantry", example = "2")
        Integer pantryAmount,

        @Schema(description = "Validity date of the pantry item", example = "2024-12-31")
        LocalDate validityDate
) {}
