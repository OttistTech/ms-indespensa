package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record PantryItemDashInfoDTO(
        Integer itemsInPantry,
        LocalDate lastPurchaseDate,
        Integer itemsCloseExpirationDate,
        Integer possibleRecipesInPantry
) {
}
