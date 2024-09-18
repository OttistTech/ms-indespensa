package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;

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
        Integer pantryItemAmount,
        LocalDate pantryItemValidityDate,
        LocalDate pantryItemPurchaseDate,
        Boolean pantryItemIsActive
) {

    public static PantryItemSimplifiedResponseDTO fromPantryItem(PantryItem pantryItem) {
        return new PantryItemSimplifiedResponseDTO(
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
