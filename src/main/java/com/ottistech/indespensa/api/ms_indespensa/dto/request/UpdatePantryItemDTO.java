package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdatePantryItemDTO (
    @NotNull(message = "Field pantryItemId is required") @Min(value = 1, message = "Field pantryItemId should be at least 1") Long pantryItemId,
    @NotNull(message = "Field pantryAmount is required") @Min(value = 0, message = "Field pantryAmount should be at least positive")  Integer pantryAmount
) {

}
