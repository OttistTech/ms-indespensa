package com.ottistech.indespensa.api.ms_indespensa.dto.cep.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CepApiResponse(

        @JsonProperty("cep")
        String cepId,

        @JsonProperty("logradouro")
        String street,

        @JsonProperty("uf")
        String state,

        @JsonProperty("localidade")
        String city
) {

        public static AddressResponse toAddressResponse(CepApiResponse cepApiResponse) {
                return new AddressResponse(
                        cepApiResponse.cepId,
                        cepApiResponse.street,
                        cepApiResponse.state,
                        cepApiResponse.city
                );
        }
}
