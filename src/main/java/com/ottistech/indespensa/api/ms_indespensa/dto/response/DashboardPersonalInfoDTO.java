package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record DashboardPersonalInfoDTO(
        Integer itemsInPantryCount,
        LocalDate lastPurchaseDate,
        Integer itemsCloseValidityDateCount,
        Integer possibleRecipesInPantryCount
) {

    public static DashboardPersonalInfoDTO fromAllDetails(Integer itemsInPantryCount, LocalDate lastPurchaseDate, Integer itemsCloseValidityDateCount, Integer possibleRecipesInPantryCount) {
        return new DashboardPersonalInfoDTO(
                itemsInPantryCount,
                lastPurchaseDate,
                itemsCloseValidityDateCount,
                possibleRecipesInPantryCount
        );
    }
}
