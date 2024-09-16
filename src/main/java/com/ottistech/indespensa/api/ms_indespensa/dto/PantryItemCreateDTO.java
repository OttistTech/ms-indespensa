package com.ottistech.indespensa.api.ms_indespensa.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PantryItemCreateDTO(
        String productEanCode,
        @NotNull(message = "Field productName is required") String productName,
        @NotNull(message = "Field productDescription is required") String productDescription,
        String productImageUrl,
        @NotNull(message = "Field productAmount is required") BigDecimal productAmount,
        @NotNull(message = "Field productUnit is required") String productUnit,
        @NotNull(message = "Field foodName is required") String foodName,
        @NotNull(message = "Field categoryName is required") String categoryName,
        @NotNull(message = "Field brandName is required") String brandName,
        @NotNull(message = "Field pantryAmount is required") Integer pantryAmount,
        @NotNull(message = "Field validityDate is required") LocalDate validityDate
) {
}
