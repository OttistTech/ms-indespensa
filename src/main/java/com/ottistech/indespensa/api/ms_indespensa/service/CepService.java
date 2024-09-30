package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.Food;
import com.ottistech.indespensa.api.ms_indespensa.repository.CepRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CepService {

    private final CepRepository cepRepository;

    public Cep getOrCreateCep(Cep cep) {
        return cepRepository.findById(cep.getCepId())
                .orElseGet(() -> cepRepository.save(cep));
    }
}
