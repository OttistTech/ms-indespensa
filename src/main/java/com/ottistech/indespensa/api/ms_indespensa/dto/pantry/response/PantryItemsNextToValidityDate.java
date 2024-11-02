package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Details of pantry items that are nearing their expiration date.")
public record PantryItemsNextToValidityDate(

        @Schema(description = "Unique identifier of the pantry item", example = "123")
        Long pantryItemId,

        @Schema(description = "Name of the product", example = "Pasta")
        String productName,

        @Schema(description = "Amount of the product in the pantry", example = "2")
        Integer amount,

        @Schema(description = "Unit of measurement for the product amount", example = "kg")
        String unit,

        @Schema(description = "Date when the product will expire", example = "2024-12-31")
        LocalDate validityDate
) {
}
