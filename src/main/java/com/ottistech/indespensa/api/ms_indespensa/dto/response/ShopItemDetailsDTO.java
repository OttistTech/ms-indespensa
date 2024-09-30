package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;

public record ShopItemDetailsDTO(
        Long itemId,
        Long userId,
        Long productId,
        String productImageUrl,
        String productFood,
        String productName,
        String productBrand,
        String productDescription,
        BigDecimal productAmount,
        String productUnit,
        Integer amount
) {
}
