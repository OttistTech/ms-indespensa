package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record ShopPurchaseHistoryItemDTO(
        LocalDate purchaseDate,
        String imageUrl,
        String productName,
        Integer amount
) {
}
