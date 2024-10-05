package com.ottistech.indespensa.api.ms_indespensa.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Difficulty {
    EASY("Fácil"),
    MEDIUM("Médio"),
    HARD("Difícil");

    private final String portuguese;

}
