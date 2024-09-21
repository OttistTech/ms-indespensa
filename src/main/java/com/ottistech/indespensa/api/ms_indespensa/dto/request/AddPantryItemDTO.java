package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddPantryItemDTO(
        @NotNull(message = "Field shopItemId is required") @Min(value = 1, message = "Field shopItemId must be at least 1") Long shopItemId,
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
