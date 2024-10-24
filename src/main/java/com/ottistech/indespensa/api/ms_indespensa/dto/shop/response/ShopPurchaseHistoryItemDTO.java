package com.ottistech.indespensa.api.ms_indespensa.dto.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Represents a purchase history item, including purchase date and details of purchased items.")
public record ShopPurchaseHistoryItemDTO(
        @Schema(description = "The date of the purchase", example = "2024-10-19")
        LocalDate purchaseDate,

        @Schema(description = "Total amount of items purchased on the day", example = "5")
        Integer dailyAmount,

        @Schema(description = "List of details of the items purchased on that day")
        List<ShopPurchaseHistoryDataDTO> historyDataItems
) {
}
