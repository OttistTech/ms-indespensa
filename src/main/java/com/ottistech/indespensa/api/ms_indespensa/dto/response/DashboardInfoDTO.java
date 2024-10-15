package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record DashboardInfoDTO(
        Integer itemsInPantry,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate lastPurchaseDate,
        Integer itemsCloseExpirationDate,
        Integer possibleRecipesInPantry
) {
}
