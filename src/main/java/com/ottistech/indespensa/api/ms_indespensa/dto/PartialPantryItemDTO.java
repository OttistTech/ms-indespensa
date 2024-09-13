package com.ottistech.indespensa.api.ms_indespensa.dto;

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

}
