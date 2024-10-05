package com.ottistech.indespensa.api.ms_indespensa.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Difficulty {
    EASY("Fácil"),
    INTER("Inter"),
    ADVANCED("Avançado");

    private final String portuguese;

}
