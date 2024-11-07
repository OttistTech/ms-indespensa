package com.ottistech.indespensa.api.ms_indespensa.dto.cep.response;

public record CepApiResponse(
        String cep, // cep_id
        String logradouro, // street
        String uf, // state
        String localidade // city
) {
}
