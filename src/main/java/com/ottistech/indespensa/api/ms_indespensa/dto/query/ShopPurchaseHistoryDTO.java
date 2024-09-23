package com.ottistech.indespensa.api.ms_indespensa.dto.query;

import java.time.LocalDate;

public record ShopPurchaseHistoryDTO(
        LocalDate purchaseDate,
        Long productId,
        String name,
        String imageUrl,
        Long amount
) {
}
