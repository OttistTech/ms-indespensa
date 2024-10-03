package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RateRecipeRequestDTO(
        @NotNull(message = "Field userId is required")
        @Min(value = 1, message = "Field userId must be at least 1")
        Long userId,
        @NotNull(message = "Field numStars is required")
        @Min(value = 0, message = "Field numStars must be 0 => numStars <= 5")
        @Max(value = 5, message = "Field numStars must be 0 => numStars <= 5")
        Integer numStars
) {
}
