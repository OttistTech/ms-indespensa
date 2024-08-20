package com.ottistech.indespensa.api.ms_indespensa.dto;

public record UserFullInfoResponse(
        Long userId,
        String type,
        String name,
        String enterpriseType,
        String email,
        String password,
        String cep,
        Integer addressNumber,
        String street,
        String city,
        String state,
        Boolean isPremium
) {
}
