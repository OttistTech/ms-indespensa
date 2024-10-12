package com.ottistech.indespensa.api.ms_indespensa.service;

import com.ottistech.indespensa.api.ms_indespensa.client.ProductClientService;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.CreateProductDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ProductResponseApiDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ProductResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.ProductSearchResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.model.Product;
import com.ottistech.indespensa.api.ms_indespensa.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductClientService productClientService;
    private final FoodService foodService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Cacheable(value = "product_by_barcode", key = "#barcode")
    public ProductResponseDTO getProductByBarcode(String barcode) {
        return productRepository.findByEanCodeNotNull(barcode)
                .map(ProductResponseDTO::fromProduct)
                .orElseGet(() -> getProductFromApi(barcode));
    }

    private ProductResponseDTO getProductFromApi(String barcode) {
        ProductResponseApiDTO productResponseApiDTO = productClientService.fetchProductByBarcode(barcode);

        if (productResponseApiDTO.success()) {
            return ProductResponseDTO.convertApiResponseToProductDTO(productResponseApiDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "We were unable to find any products with this barcode");
        }
    }

    public Product getOrCreateProduct(CreateProductDTO createProductDTO) {
        Optional<Product> existingProductByEanCode = productRepository.findByEanCodeNotNull(createProductDTO.productEanCode());

        if (existingProductByEanCode.isPresent()) {
            return existingProductByEanCode.get();
        }

        Optional<Product> existingProductByName = productRepository.findByNameNotNull(createProductDTO.productName());

        return existingProductByName.orElseGet(() -> createNewProduct(createProductDTO));
    }

    private Product createNewProduct(CreateProductDTO createProductDTO) {
        Product product = new Product();

        product.setEanCode(createProductDTO.productEanCode());
        product.setName(createProductDTO.productName());
        product.setImageUrl(createProductDTO.productImageUrl());
        product.setDescription(createProductDTO.productDescription());
        product.setAmount(createProductDTO.productAmount());
        product.setUnit(createProductDTO.productUnit());
        product.setType(createProductDTO.type());

        product.setFoodId(foodService.getOrCreateFood(createProductDTO.foodName()));
        product.setBrandId(brandService.getOrCreateBrand(createProductDTO.brandName()));
        product.setCategoryId(categoryService.getOrCreateCategory(createProductDTO.categoryName()));

        productRepository.save(product);

        return product;
    }

    @Cacheable(value = "product_by_pattern", key = "#pattern")
    public List<ProductSearchResponseDTO> findProductsByNamePattern(String pattern) {
        List<ProductSearchResponseDTO> productsFound = productRepository.findAllByNameStartingWithIgnoreCase(pattern);

        if(productsFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products matching name pattern");
        }

        return productsFound;
    }
}
