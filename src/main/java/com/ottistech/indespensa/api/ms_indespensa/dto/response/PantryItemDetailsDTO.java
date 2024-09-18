package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PantryItemDetailsDTO(
    Long itemId,
    Long userId,
    String productImageUrl,
    String productFood,
    String productName,
    String productBrand,
    String productDescription,
    BigDecimal productAmount,
    String productUnit,
    Integer amount,
    LocalDate validityDate
) {
}
