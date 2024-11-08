package com.ottistech.indespensa.api.ms_indespensa.dto.cep.response;

public record AddressResponse(
        String cepId,
        String street,
        String state,
        String city
) {
}
