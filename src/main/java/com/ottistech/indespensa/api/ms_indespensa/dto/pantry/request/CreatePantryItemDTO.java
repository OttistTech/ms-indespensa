package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request;

import com.ottistech.indespensa.api.ms_indespensa.dto.shop.request.AddShopItemDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.request.CreateProductDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO for creating a new pantry item.")
public record CreatePantryItemDTO(

        @Schema(description = "The EAN code of the product", example = "1234567890123")
        String productEanCode,

        @Schema(description = "The name of the product", example = "Almond Milk")
        @NotNull(message = "Field productName is required")
        String productName,

        @Schema(description = "A brief description of the product", example = "Organic unsweetened almond milk")
        @NotNull(message = "Field productDescription is required")
        String productDescription,

        @Schema(description = "The URL of the product image", example = "https://example.com/image.jpg")
        String productImageUrl,

        @Schema(description = "The amount of product to add to the pantry", example = "2.0")
        @NotNull(message = "Field productAmount is required")
        @Min(value = 0, message = "Field productAmount should be at least a positive number")
        BigDecimal productAmount,

        @Schema(description = "The unit of measurement for the product amount", example = "L")
        @NotNull(message = "Field productUnit is required")
        String productUnit,

        @Schema(description = "The type of product (e.g., food, beverage)", example = "Beverage")
        String productType,

        @Schema(description = "The name of the food item", example = "Almonds")
        @NotNull(message = "Field foodName is required")
        String foodName,

        @Schema(description = "The category of the product", example = "Dairy Alternatives")
        @NotNull(message = "Field categoryName is required")
        String categoryName,

        @Schema(description = "The brand name of the product", example = "Brand A")
        @NotNull(message = "Field brandName is required")
        String brandName,

        @Schema(description = "The amount of this pantry item to be added", example = "5")
        @NotNull(message = "Field pantryAmount is required")
        @Min(value = 1, message = "Field productAmount should be at least 1")
        Integer pantryAmount,

        @Schema(description = "The expiration date of the pantry item", example = "2024-12-31")
        @NotNull(message = "Field validityDate is required")
        LocalDate validityDate
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
