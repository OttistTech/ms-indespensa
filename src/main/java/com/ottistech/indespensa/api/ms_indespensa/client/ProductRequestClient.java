package com.ottistech.indespensa.api.ms_indespensa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "BarcodeAPIClient",
        url = "https://api.upcdatabase.org/",
        configuration = FeignClientProperties.FeignClientConfiguration.class
)
public interface ProductRequestClient {

    @GetMapping("/product/{barcode}")
    ResponseEntity<String> getProductByBarcode(@PathVariable("barcode") String barcode);
}
