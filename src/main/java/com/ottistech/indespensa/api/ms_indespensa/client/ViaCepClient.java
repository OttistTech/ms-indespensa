package com.ottistech.indespensa.api.ms_indespensa.client;

import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ViaCepClient {

    private final ViaCepRequest viaCepRequest;

    @Cacheable(value = {"cep"}, key = "#cep")
    public CepApiResponse fetchAddressByCep(String cep) {
        try {
            return viaCepRequest.findCep(cep).getBody();
        } catch (FeignException.BadRequest e) {
            throw new IllegalArgumentException("O CEP informado é inválido: " + cep, e);
        }
    }
}
