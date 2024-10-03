package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;

public record RecipeIngredientDetailsDTO(
        String foodName,
        BigDecimal amount,
        String unit,
        Boolean isEssential,
        Boolean isInPantry
) {
}
