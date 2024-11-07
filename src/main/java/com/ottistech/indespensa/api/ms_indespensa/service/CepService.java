package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.client.ViaCepClient;
import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.repository.CepRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CepService {

    private final CepRepository cepRepository;
    private final ViaCepClient viaCepClient;

    public Cep getOrCreateCep(Cep cep) {
        return cepRepository.findById(cep.getCepId())
                .orElseGet(() -> cepRepository.save(cep));
    }

    public CepApiResponse fetchCepFromViaCep(String cep) {

        return viaCepClient.fetchAddressByCep(cep);
    }

    public CepApiResponse findCep(String cep) {
        Optional<CepApiResponse> optionalCep = cepRepository.findCep(cep);

        return optionalCep.orElseGet(() -> fetchCepFromViaCep(cep));

    }
}
