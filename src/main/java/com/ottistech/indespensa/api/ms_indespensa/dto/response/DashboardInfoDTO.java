package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record DashboardInfoDTO(
        Integer itemsInPantryCount,
        LocalDate lastPurchaseDate,
        Integer itemsCloseValidityDateCount,
        Integer possibleRecipesInPantryCount
) {

    public static DashboardInfoDTO fromAllDetails(Integer itemsInPantryCount, LocalDate lastPurchaseDate, Integer itemsCloseValidityDateCount, Integer possibleRecipesInPantryCount) {
        return new DashboardInfoDTO(
                itemsInPantryCount,
                lastPurchaseDate,
                itemsCloseValidityDateCount,
                possibleRecipesInPantryCount
        );
    }
}
