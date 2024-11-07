package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.client.ViaCepClient;
import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
@AllArgsConstructor
public class AddressController {

    private final ViaCepClient viaCepClient;

    @GetMapping("/{cep}")
    public ResponseEntity<CepApiResponse> findCep(
            @PathVariable("cep")
            String cep
    ) {

        CepApiResponse address = viaCepClient.fetchAddressByCep(cep);

        return ResponseEntity.ok(address);
    }
}
