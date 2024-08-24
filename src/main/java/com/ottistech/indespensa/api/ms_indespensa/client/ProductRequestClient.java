package com.ottistech.indespensa.api.ms_indespensa.client;

import com.ottistech.indespensa.api.ms_indespensa.dto.ProductResponseApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        value = "BarcodeAPIClient",
        url = "https://api.upcdatabase.org/",
        configuration = FeignClientProperties.FeignClientConfiguration.class
)
public interface ProductRequestClient {

    @GetMapping("/product/{barcode}")
    Optional<ProductResponseApiDTO> getProductByBarcode(@PathVariable("barcode") String barcode);
}
