package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record PantryItemDashInfoDTO(
        PantryItemCountDTO countItemsInPantry,
        PantryItemLastPurchaseDateDTO pantryItemLastPurchaseDateDTO,
        PantryItemCountCloseExpirationDateDTO pantryItemCountCloseExpirationDateDTO,
        PantryItemCountPossibleRecipesDTO pantryItemCountPossibleRecipesDTO
) {
}
