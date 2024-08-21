package com.ottistech.indespensa.api.ms_indespensa.dto;

import java.util.Date;

public record UserFullInfoResponse(
        Long userId,
        String type,
        String name,
        Date birthDate,
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
