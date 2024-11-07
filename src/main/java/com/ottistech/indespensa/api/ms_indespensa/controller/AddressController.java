package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import com.ottistech.indespensa.api.ms_indespensa.service.CepService;
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

    private final CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<CepApiResponse> findCep(
            @PathVariable("cep")
            String cep
    ) {

        CepApiResponse address = cepService.findCep(cep);

        return ResponseEntity.ok(address);
    }
}
