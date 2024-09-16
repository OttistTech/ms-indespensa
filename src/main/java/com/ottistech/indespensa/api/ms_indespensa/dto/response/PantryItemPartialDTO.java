package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PantryItemPartialDTO(
        Long userId,
        Long pantryItemId,
        String name,
        String imageUrl,
        BigDecimal productAmount,
        String productUnit,
        Integer pantryAmount,
        LocalDate validityDate
) {}
