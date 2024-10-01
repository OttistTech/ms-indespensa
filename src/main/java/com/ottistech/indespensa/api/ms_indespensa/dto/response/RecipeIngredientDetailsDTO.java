package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Food;

import java.math.BigDecimal;

public record RecipeIngredientDetailsDTO(
        String foodName,
        BigDecimal amount,
        String unit,
        Boolean isEssential
) {
}
