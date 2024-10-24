package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.request;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "DTO for adding a pantry item from a shop item")
public record AddPantryItemDTO(
        @Schema(description = "ID of the shop item to be added to the pantry", example = "123")
        @NotNull(message = "Field shopItemId is required")
        @Min(value = 1, message = "Field shopItemId must be at least 1")
        Long shopItemId,

        @Schema(description = "Optional validity date for the pantry item", example = "2024-12-31", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate validityDate
) {
    public PantryItem toPantryItem(User user, Product product, int amount, LocalDate validityDate) {
        return new PantryItem(
            user,
            product,
            amount,
            validityDate
        );
    }
}
