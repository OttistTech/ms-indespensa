package com.ottistech.indespensa.api.ms_indespensa.dto.cep.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CepApiResponse(

        @JsonProperty("cep_id")
        String cep,

        @JsonProperty("street")
        String logradouro,

        @JsonProperty("state")
        String uf,

        @JsonProperty("city")
        String localidade
) {
}
