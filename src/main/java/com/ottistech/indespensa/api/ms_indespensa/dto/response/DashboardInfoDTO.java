package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record DashboardInfoDTO(
        Integer itemsInPantry,
        LocalDate lastPurchaseDate,
        Integer itemsCloseValidityDate,
        Integer possibleRecipesInPantry
) {
}
