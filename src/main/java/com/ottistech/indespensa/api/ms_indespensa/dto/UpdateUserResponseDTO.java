package com.ottistech.indespensa.api.ms_indespensa.dto;

public record UpdateUserResponseDTO (
        String name,
        String enterpriseType,
        String email,
        String cep,
        Integer addressNumber,
        String street,
        String city,
        String state
) {
}
