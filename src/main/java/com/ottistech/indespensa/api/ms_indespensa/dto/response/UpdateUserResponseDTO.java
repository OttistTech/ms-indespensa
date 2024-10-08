package com.ottistech.indespensa.api.ms_indespensa.dto.response;

public record UpdateUserResponseDTO (
        String name,
        String email,
        String cep,
        Integer addressNumber,
        String street,
        String city,
        String state
) {
}
