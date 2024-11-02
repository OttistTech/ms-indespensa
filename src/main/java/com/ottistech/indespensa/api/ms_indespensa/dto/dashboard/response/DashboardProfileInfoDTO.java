package com.ottistech.indespensa.api.ms_indespensa.dto.dashboard.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Profile dashboard information for a user.")
public record DashboardProfileInfoDTO(

        @Schema(description = "The total number of items currently in the user's pantry.", example = "25")
        Integer itemsInPantryCount,

        @Schema(description = "The total number of purchases made by the user.", example = "10")
        Integer purchasesMadeCount,

        @Schema(description = "The total number of products that have already expired.", example = "2")
        Integer productsAlreadyExpiredCount,

        @Schema(description = "The total number of recipes made by the user.", example = "5")
        Integer recipesMadeCount
) {

    public static DashboardProfileInfoDTO fromAllDetails(Integer itemsInPantryCount, Integer purchasesMadeCount, Integer productsAlreadyExpiredCount, Integer recipesMadeCount) {
        return new DashboardProfileInfoDTO(
                itemsInPantryCount,
                purchasesMadeCount,
                productsAlreadyExpiredCount,
                recipesMadeCount
        );
    }
}
