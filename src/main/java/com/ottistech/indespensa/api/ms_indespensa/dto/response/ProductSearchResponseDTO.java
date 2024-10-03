package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record ProductSearchResponseDTO(
        Long productId,
        String name,
        String imageUrl
) {
}
