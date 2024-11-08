package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Address", description = "Endpoints related to Address management")
public interface AddressContract {

    @Operation(summary = "Find CEP", description = "Retrieves address information based on the provided CEP. First checks the database, and if not found, queries the ViaCep API. If the CEP doesn't exist, returns a 404 error.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address information retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CepApiResponse.class))
            ),

            @ApiResponse(responseCode = "404", description = "CEP not found in both database and external API",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"message\": \"CEP not found\" }"))
            ),

            @ApiResponse(responseCode = "400", description = "Invalid CEP format",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"message\": \"Invalid CEP format\" }"))
            )
    })
    ResponseEntity<CepApiResponse> findCep(
            @Parameter(in = ParameterIn.PATH, description = "User's CEP with 8 chars", example = "06365890")
            String cep
    );
}
