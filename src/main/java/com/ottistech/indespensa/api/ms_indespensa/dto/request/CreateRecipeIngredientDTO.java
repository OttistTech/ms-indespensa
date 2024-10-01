package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRecipeIngredientDTO(
        @NotNull(message = "Field foodId is required") Long foodId,
        @Digits(integer = 10, fraction = 2) BigDecimal amount,
        String unit,
        @NotNull(message = "Field isEssential is required") Boolean isEssential
) {
}
