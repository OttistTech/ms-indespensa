package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record DashboardProfileInfoDTO(
        Integer itemsInPantryCount,
        Integer purchasesMadeCount,
        Integer productsAlreadyExpiredCount,
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
