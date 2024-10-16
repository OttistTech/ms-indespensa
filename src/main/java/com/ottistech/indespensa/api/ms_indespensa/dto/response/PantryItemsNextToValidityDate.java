package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record PantryItemsNextToValidityDate(
        Long pantryItemId,
        String productName,
        Integer amount,
        String unit,
        LocalDate validityDate
) {
}
