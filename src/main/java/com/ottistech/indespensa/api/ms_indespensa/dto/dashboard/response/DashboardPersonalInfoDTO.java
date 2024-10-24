package com.ottistech.indespensa.api.ms_indespensa.dto.dashboard.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Personal dashboard information for a user.")
public record DashboardPersonalInfoDTO(

        @Schema(description = "The total number of items currently in the pantry.", example = "25")
        Integer itemsInPantryCount,

        @Schema(description = "The date of the last purchase made by the user.", example = "2024-10-15")
        LocalDate lastPurchaseDate,

        @Schema(description = "The number of items that are close to their validity date.", example = "5")
        Integer itemsCloseValidityDateCount,

        @Schema(description = "The number of recipes that can be made with the items currently in the pantry.", example = "10")
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
