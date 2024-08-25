package com.ottistech.indespensa.api.ms_indespensa.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        String eanCode,
        String name,
        String imageUrl,
        String foodName,
        String categoryName,
        String description,
        String brandName,
        BigDecimal amount,
        String unit,
        String type
) {
}
