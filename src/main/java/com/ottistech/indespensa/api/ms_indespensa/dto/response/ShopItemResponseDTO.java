package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record ShopItemResponseDTO(
        Long listItemId,
        Long userId,
        String productName,
        String imageUrl,
        Integer amount,
        BigDecimal productAmount,
        String productUnit
) {
}
