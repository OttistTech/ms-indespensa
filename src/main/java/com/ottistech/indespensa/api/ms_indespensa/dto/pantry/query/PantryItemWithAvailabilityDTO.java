package com.ottistech.indespensa.api.ms_indespensa.dto.pantry.query;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;

public record PantryItemWithAvailabilityDTO(
        PantryItem pantryItem,
        boolean isInPantry
) {
}
