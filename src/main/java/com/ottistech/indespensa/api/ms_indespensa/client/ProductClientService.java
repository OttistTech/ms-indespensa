package com.ottistech.indespensa.api.ms_indespensa.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ProductResponseApiDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.JsonParcealizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductClientService {

    private final ProductRequestClient productRequestClient;
    private final ObjectMapper objectMapper;

    public ProductClientService(ProductRequestClient productRequestClient, ObjectMapper objectMapper) {
        this.productRequestClient = productRequestClient;
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ProductResponseApiDTO fetchProductByBarcode(String barcode) {
        ResponseEntity<String> responseEntity = productRequestClient.getProductByBarcode(barcode);
        String responseBody = responseEntity.getBody();

        if (responseBody == null || responseBody.isEmpty()) {
            throw new RuntimeException("The response from external API is empty");
        }

        String json = extractJson(responseBody);

        try {
            return objectMapper.readValue(json, ProductResponseApiDTO.class);
        } catch (JsonProcessingException e) {
            throw new JsonParcealizationException("Error while processing API response");
        }
    }

    private String extractJson(String responseBody) {
        int jsonStartIndex = responseBody.indexOf("{");
        int jsonEndIndex = responseBody.lastIndexOf("}") + 1;

        if (jsonStartIndex != -1 && jsonEndIndex > jsonStartIndex) {
            return responseBody.substring(jsonStartIndex, jsonEndIndex);
        }

        return "";
    }
}


