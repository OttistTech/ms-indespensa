package com.ottistech.indespensa.api.ms_indespensa.client;

import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "ViaCep",
        url = "https://viacep.com.br/ws",
        configuration = FeignClientProperties.FeignClientConfiguration.class
)
public interface ViaCepRequest {

    @GetMapping("/{cep}/json")
    ResponseEntity<CepApiResponse> findCep(
            @PathVariable("cep")
            String cep
    );
}
