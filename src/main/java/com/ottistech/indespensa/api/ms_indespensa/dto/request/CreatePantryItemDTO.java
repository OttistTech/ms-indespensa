package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePantryItemDTO(
        String productEanCode,
        @NotNull(message = "Field productName is required") String productName,
        @NotNull(message = "Field productDescription is required") String productDescription,
        String productImageUrl,
        @NotNull(message = "Field productAmount is required") @Min(value = 0, message = "Field productAmount should be at least a positive number") BigDecimal productAmount,
        @NotNull(message = "Field productUnit is required") String productUnit,
        String productType,
        @NotNull(message = "Field foodName is required") String foodName,
        @NotNull(message = "Field categoryName is required") String categoryName,
        @NotNull(message = "Field brandName is required") String brandName,
        @NotNull(message = "Field pantryAmount is required") @Min(value = 1, message = "Field productAmount should be at least 1") Integer pantryAmount,
        @NotNull(message = "Field validityDate is required") LocalDate validityDate
) {

    public CreateProductDTO toProductDto() {
        return new CreateProductDTO(
                this.productEanCode(),
                this.productName(),
                this.productDescription(),
                this.productImageUrl(),
                this.productAmount(),
                this.productUnit(),
                this.productType(),
                this.foodName(),
                this.categoryName(),
                this.brandName()
        );
    }

    public AddShopItemDTO toAddShopItemDTO(Long productId) {
        return new AddShopItemDTO(
                productId,
                this.pantryAmount
        );
    }
}
