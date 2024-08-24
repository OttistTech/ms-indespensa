package com.ottistech.indespensa.api.ms_indespensa.dto;

import java.util.List;
import java.util.Map;

public record ProductResponseApiDTO(
        String title,
        String alias,
        String description,
        String brand,
        String category,
        String barcode,
        List<String> images,
        Map<String, String> metadata
) {
}
