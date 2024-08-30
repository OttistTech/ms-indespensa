package com.ottistech.indespensa.api.ms_indespensa.dto;

import com.ottistech.indespensa.api.ms_indespensa.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PantryItemSimplifiedResponseDTO(
        String productEanCode,
        String productName,
        String productImageUrl,
        String foodName,
        String categoryName,
        String productDescription,
        String brandName,
        BigDecimal productAmount,
        String productUnit,
        String productType,
        Long userId,
        String type,
        String name,
        String enterpriseType,
        Integer pantryItemAmount,
        LocalDate pantryItemValidityDate,
        LocalDate pantryItemPurchaseDate,
        Boolean pantryItemIsActive
) {
}