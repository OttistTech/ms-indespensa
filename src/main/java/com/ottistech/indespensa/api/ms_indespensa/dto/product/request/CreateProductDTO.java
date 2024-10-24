package com.ottistech.indespensa.api.ms_indespensa.dto.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductDTO(
        String productEanCode,
        @NotNull(message = "Field productName is required") String productName,
        @NotNull(message = "Field productDescription is required") String productDescription,
        String productImageUrl,
        @NotNull(message = "Field productAmount is required") @Min(value = 0, message = "Field productAmount should be at least a positive number") BigDecimal productAmount,
        @NotNull(message = "Field productUnit is required") String productUnit,
        String type,
        @NotNull(message = "Field foodName is required") String foodName,
        @NotNull(message = "Field foodName is required") String categoryName,
        @NotNull(message = "Field foodName is required") String brandName
) {

}
