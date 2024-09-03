package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.client.ProductClientService;
import com.ottistech.indespensa.api.ms_indespensa.client.ProductRequestClient;
import com.ottistech.indespensa.api.ms_indespensa.dto.ProductResponseApiDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.ProductResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.exception.EanCodeNotFoundException;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.repository.BrandRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.CategoryRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.FoodRepository;
import com.ottistech.indespensa.api.ms_indespensa.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRequestClient productRequestClient;
    private FoodRepository foodRepository;
    private CategoryRepository categoryRepository;
    private BrandRepository brandRepository;
    private final ProductClientService productClientService;

    public ProductResponseDTO getProductByBarcode(String barcode) {
        Optional<Product> productOptional = productRepository.findByEanCodeNotNull(barcode);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            return new ProductResponseDTO(
                    product.getEanCode(),
                    product.getName(),
                    product.getImageUrl(),
                    product.getFoodId().getFoodName(),
                    product.getCategoryId().getCategoryName(),
                    product.getDescription(),
                    product.getBrandId().getBrandName(),
                    product.getAmount(),
                    product.getUnit(),
                    product.getType()
            );
        }

        ProductResponseApiDTO productResponseApiDTO = productClientService.fetchProductByBarcode(barcode);

        if (productResponseApiDTO.success()) {
                return convertApiResponseToProductDTO(productResponseApiDTO);
        } else {
                throw new EanCodeNotFoundException("We were unable to find any products with this barcode");
        }

    }

    public ProductResponseDTO convertApiResponseToProductDTO(ProductResponseApiDTO apiResponse) {
        BigDecimal amount = apiResponse.metadata() != null ? parseAmount(apiResponse.metadata()) : null;
        String unit = apiResponse.metadata() != null ? parseUnit(apiResponse.metadata()) : null;

        return new ProductResponseDTO(
                apiResponse.barcode() == null || apiResponse.barcode().isEmpty() ? null : apiResponse.barcode(),
                apiResponse.title() == null || apiResponse.title().isEmpty() ? null : apiResponse.title(),
                null,
                apiResponse.alias() == null || apiResponse.alias().isEmpty() ? null : apiResponse.alias(),
                apiResponse.category() == null || apiResponse.category().isEmpty() ? null : apiResponse.category(),
                apiResponse.description() == null || apiResponse.description().isEmpty() ? null : apiResponse.description(),
                apiResponse.brand() == null || apiResponse.barcode().isEmpty() ? null : apiResponse.brand(),
                amount,
                unit,
                null
        );
    }

    private BigDecimal parseAmount(Map<String, String> metadata) {
        String quantity = metadata.get("quantity");

        if (quantity != null && !quantity.isEmpty()) {
            String[] quantityParts = quantity.split(", ");

            if (quantityParts.length > 0) {
                String[] amountAndUnit = quantityParts[0].split(" ");
                if (amountAndUnit.length >= 1) {
                    try {
                        return new BigDecimal(amountAndUnit[0]);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter quantidade: " + e.getMessage());
                    }
                }
            }
        }

        return null;
    }

    private String parseUnit(Map<String, String> metadata) {
        String quantity = metadata.get("quantity");

        if (quantity != null && !quantity.isEmpty()) {
            String[] quantityParts = quantity.split(", ");

            if (quantityParts.length > 0) {
                String[] amountAndUnit = quantityParts[0].split(" ");
                if (amountAndUnit.length == 2) {
                    return amountAndUnit[1];
                }
            }
        }

        return null;
    }

}
