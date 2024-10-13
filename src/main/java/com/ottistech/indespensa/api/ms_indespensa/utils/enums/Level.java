package com.ottistech.indespensa.api.ms_indespensa.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
    EASY("Fácil"),
    INTER("Inter"),
    ADVANCED("Avançado");

    final String stringLevel;
}
