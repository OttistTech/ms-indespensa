package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateProductItemAmountDTO(
    @NotNull(message = "Field itemId is required") @Min(value = 1, message = "Field itemId should be at least 1") Long itemId,
    @NotNull(message = "Field amount is required") @Min(value = 0, message = "Field amount should be at least positive")  Integer amount
) {

}
