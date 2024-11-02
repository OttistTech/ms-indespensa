package com.ottistech.indespensa.api.ms_indespensa.dto.product.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.utils.ParsingUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Product details retrieved from the barcode search.")
public record ProductResponseDTO(

        @Schema(description = "The EAN code (barcode) of the product.", example = "0123456789012")
        String eanCode,

        @Schema(description = "The name of the product.", example = "Chocolate Bar")
        String name,

        @Schema(description = "The URL of the product's image.", example = "https://example.com/images/chocolate-bar.jpg")
        String imageUrl,

        @Schema(description = "The name of the food category the product belongs to.", example = "Snacks")
        String foodName,

        @Schema(description = "The name of the category the product belongs to.", example = "Confectionery")
        String categoryName,

        @Schema(description = "A brief description of the product.", example = "A delicious chocolate bar made with rich cocoa.")
        String description,

        @Schema(description = "The brand of the product.", example = "BrandName Inc.")
        String brandName,

        @Schema(description = "The quantity of the product.", example = "100.00")
        BigDecimal amount,

        @Schema(description = "The unit of measurement for the product amount.", example = "g")
        String unit,

        @Schema(description = "The type of the product.", example = "Food")
        String type
) {

    public static ProductResponseDTO fromProduct(Product product) {
        return new ProductResponseDTO(
                product.getEanCode(),
                product.getName(),
                product.getImageUrl(),
                product.getFoodId().getFoodName(),
                product.getCategoryId().getCategoryName(),
                product.getDescription(),
                product.getBrandId().getBrandName(),
                product.getAmount(),
                product.getUnit(),
                product.getType()
        );
    }

    public static ProductResponseDTO convertApiResponseToProductDTO(ProductResponseApiDTO apiResponse) {
        BigDecimal amount = ParsingUtils.parseAmountFromMetadata(apiResponse.metadata());
        String unit = ParsingUtils.parseUnitFromMetadata(apiResponse.metadata());

        return new ProductResponseDTO(
                ParsingUtils.defaultIfEmpty(apiResponse.barcode()),
                ParsingUtils.defaultIfEmpty(apiResponse.title()),
                null,
                ParsingUtils.defaultIfEmpty(apiResponse.alias()),
                ParsingUtils.defaultIfEmpty(apiResponse.category()),
                ParsingUtils.defaultIfEmpty(apiResponse.description()),
                ParsingUtils.defaultIfEmpty(apiResponse.brand()),
                amount,
                unit,
                null
        );
    }

}
