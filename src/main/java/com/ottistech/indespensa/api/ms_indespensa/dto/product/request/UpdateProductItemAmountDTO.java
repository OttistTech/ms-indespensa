package com.ottistech.indespensa.api.ms_indespensa.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for updating the amount of a pantry item.")
public record UpdateProductItemAmountDTO(

        @Schema(description = "Unique identifier of the pantry item to be updated.", example = "1")
        @NotNull(message = "Field itemId is required")
        @Min(value = 1, message = "Field itemId should be at least 1")
        Long itemId,

        @Schema(description = "New amount for the pantry item. Must be a non-negative integer.", example = "10")
        @NotNull(message = "Field amount is required")
        @Min(value = 0, message = "Field amount should be at least positive")
        Integer amount
) {

}