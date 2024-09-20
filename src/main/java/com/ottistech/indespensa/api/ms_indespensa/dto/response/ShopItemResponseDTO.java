package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.PantryItem;

import java.math.BigDecimal;

public record ShopItemResponseDTO(
        Long listItemId,
        Long userId,
        String productName,
        String imageUrl,
        Integer amount,
        BigDecimal productAmount,
        String productUnit
) {}
