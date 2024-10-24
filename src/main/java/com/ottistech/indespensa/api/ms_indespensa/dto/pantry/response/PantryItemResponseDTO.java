package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.response;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Response DTO for a pantry item.")
public record PantryItemResponseDTO(

        @Schema(description = "The EAN code of the product", example = "1234567890123")
        String productEanCode,

        @Schema(description = "The name of the product", example = "Almond Milk")
        String productName,

        @Schema(description = "The URL of the product image", example = "https://example.com/image.jpg")
        String productImageUrl,

        @Schema(description = "The name of the food item", example = "Almonds")
        String foodName,

        @Schema(description = "The category of the product", example = "Dairy Alternatives")
        String categoryName,

        @Schema(description = "A brief description of the product", example = "Organic unsweetened almond milk")
        String productDescription,

        @Schema(description = "The brand name of the product", example = "Brand A")
        String brandName,

        @Schema(description = "The amount of product in the pantry", example = "2.5")
        BigDecimal productAmount,

        @Schema(description = "The unit of measurement for the product amount", example = "L")
        String productUnit,

        @Schema(description = "The type of product (e.g., food, beverage)", example = "Beverage")
        String productType,

        @Schema(description = "The ID of the user who owns the pantry item", example = "1")
        Long userId,

        @Schema(description = "The amount of this pantry item in the user's pantry", example = "5")
        Integer pantryItemAmount,

        @Schema(description = "The expiration date of the pantry item", example = "2024-12-31")
        LocalDate pantryItemValidityDate,

        @Schema(description = "The purchase date of the pantry item", example = "2024-01-15")
        LocalDate pantryItemPurchaseDate,

        @Schema(description = "Indicates whether the pantry item is currently active", example = "true")
        Boolean pantryItemIsActive
) {

    public static PantryItemResponseDTO fromPantryItem(PantryItem pantryItem) {
        return new PantryItemResponseDTO(
                pantryItem.getProduct().getEanCode(),
                pantryItem.getProduct().getName(),
                pantryItem.getProduct().getImageUrl(),
                pantryItem.getProduct().getFoodId().getFoodName(),
                pantryItem.getProduct().getCategoryId().getCategoryName(),
                pantryItem.getProduct().getDescription(),
                pantryItem.getProduct().getBrandId().getBrandName(),
                pantryItem.getProduct().getAmount(),
                pantryItem.getProduct().getUnit(),
                pantryItem.getProduct().getType(),
                pantryItem.getUser().getUserId(),
                pantryItem.getAmount(),
                pantryItem.getValidityDate(),
                pantryItem.getPurchaseDate(),
                pantryItem.getIsActive()
        );
    }
}
