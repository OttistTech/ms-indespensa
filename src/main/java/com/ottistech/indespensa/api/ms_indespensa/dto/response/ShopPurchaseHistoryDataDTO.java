package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record ShopPurchaseHistoryDataDTO(
        Long productId,
        String productName,
        String imageUrl,
        Long amount
) {
}
