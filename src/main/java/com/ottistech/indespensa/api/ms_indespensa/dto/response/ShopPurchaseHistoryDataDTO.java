package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;

public record ShopPurchaseHistoryDataDTO(
        String productName,
        String imageUrl,
        Long amount
) {
}
