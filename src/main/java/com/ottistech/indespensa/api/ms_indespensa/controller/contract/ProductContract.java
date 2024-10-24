package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.product.response.ProductResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.product.response.ProductSearchResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Product", description = "Endpoints related to Product management")
public interface ProductContract {

    @Operation(summary = "Search a product by barcode",
            description = "Retrieves product details based on the provided barcode. If the product is found, it returns the product information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product details",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))
            ),

            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    ResponseEntity<ProductResponseDTO> searchProductByBarcode(
            @Parameter(in = ParameterIn.PATH, description = "The barcode of the product to search", required = true, example = "0123456789012")
            String barcode
    );

    @Operation(summary = "Search products by name", description = "Retrieves a list of products that match the given name pattern.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a list of matching products",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductSearchResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    ResponseEntity<List<ProductSearchResponseDTO>> searchProductByName(
            @Parameter(in = ParameterIn.QUERY, description = "Pattern to search for in product names", required = true, example = "Chocolate")
            String pattern
    );

}
