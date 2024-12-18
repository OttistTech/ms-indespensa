package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.controller.contract.ProductContract;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.response.ProductResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.response.ProductSearchResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController implements ProductContract {

    private final ProductService productService;

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductResponseDTO> searchProductByBarcode(
            @PathVariable("barcode")
            String barcode
    ) {
        ProductResponseDTO productResponseDTO = productService.getProductByBarcode(barcode);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchResponseDTO>> searchProductByName(
            @RequestParam("pattern")
            String pattern
    ) {
        List<ProductSearchResponseDTO> productsFound = productService.findProductsByNamePattern(pattern);

        return ResponseEntity.status(HttpStatus.OK).body(productsFound);
    }

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductResponseDTO> findProductById(
            @PathVariable("productId")
            Long productId
    ) {
        ProductResponseDTO product = productService.findById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
