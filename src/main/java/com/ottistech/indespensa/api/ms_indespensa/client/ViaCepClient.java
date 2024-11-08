package com.ottistech.indespensa.api.ms_indespensa.client;

import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.AddressResponse;
import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ViaCepClient {

    private final ViaCepRequest viaCepRequest;

    @Cacheable(value = {"cep"}, key = "#cep")
    public AddressResponse fetchAddressByCep(String cep) {

        validateCep(cep);
        CepApiResponse cepApiResponse = viaCepRequest.findCep(cep).getBody();

        assert cepApiResponse != null;
        return CepApiResponse.toAddressResponse(cepApiResponse);
    }

    private void validateCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CEP deve ter 8 caracteres: " + cep);
        }
    }
}
