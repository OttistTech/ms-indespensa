package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.ProductResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductResponseDTO> searchProductByBarcode(@PathVariable("barcode") String barcode) {
        ProductResponseDTO productResponseDTO = productService.getProductByBarcode(barcode);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }
}
