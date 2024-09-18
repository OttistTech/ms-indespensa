package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.utils.ParsingUtils;

import java.math.BigDecimal;

public record ProductResponseDTO(
        String eanCode,
        String name,
        String imageUrl,
        String foodName,
        String categoryName,
        String description,
        String brandName,
        BigDecimal amount,
        String unit,
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
