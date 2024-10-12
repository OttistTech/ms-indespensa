package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ShopPurchaseHistoryItemDTO(
        LocalDate purchaseDate,
        Integer dailyAmount,
        List<ShopPurchaseHistoryDataDTO> historyDataItems
) {
}
