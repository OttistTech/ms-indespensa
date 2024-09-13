package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;

public record ListItemResponseDTO(
        String productName,
        String imageUrl,
        Integer amount,
        BigDecimal productAmount,
        String productUnit
) {
}
