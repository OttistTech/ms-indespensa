package com.ottistech.indespensa.api.ms_indespensa.dto;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record PartialPantryItemDTO(
        Long userId,
        Long pantryItemId,
        String name,
        String imageUrl,
        BigDecimal productAmount,
        String productUnit,
        Integer pantryAmount,
        LocalDate validityDate
) {

    public static PartialPantryItemDTO fromPantryItem(PantryItem pantryItem) {
        return new PartialPantryItemDTO(
            pantryItem.getUser().getUserId(),
            pantryItem.getPantryItemId(),
            pantryItem.getProduct().getName(),
            pantryItem.getProduct().getImageUrl(),
            pantryItem.getProduct().getAmount(),
            pantryItem.getProduct().getUnit(),
            pantryItem.getAmount(),
            pantryItem.getValidityDate()
        );
    }

}
