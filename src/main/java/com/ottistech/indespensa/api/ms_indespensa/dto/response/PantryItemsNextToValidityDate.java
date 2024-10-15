package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record PantryItemsNextToValidityDate(
        String productName,
        Integer amount,
        String unit,
        LocalDate validityDate
) {
}
