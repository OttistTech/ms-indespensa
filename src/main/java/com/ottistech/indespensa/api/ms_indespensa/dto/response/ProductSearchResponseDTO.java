package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record ProductSearchResponseDTO(
        Long productId,
        Long foodId,
        String productName,
        String foodName,
        String imageUrl
) {
}
