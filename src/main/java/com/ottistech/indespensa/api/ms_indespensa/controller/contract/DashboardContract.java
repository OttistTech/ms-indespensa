package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.dashboard.response.DashboardPersonalInfoDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.dashboard.response.DashboardProfileInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Dashboard", description = "Endpoints related to Dashboards management")
public interface DashboardContract {

    @Operation(summary = "Retrieve personal dashboard information", description = "Fetches the dashboard information for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved dashboard information",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DashboardPersonalInfoDTO.class))),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<DashboardPersonalInfoDTO> getDashInfo(
            @Parameter(in = ParameterIn.PATH, description = "The ID of the user whose dashboard information is being retrieved.", required = true, example = "1")
            Long userId
    );

    @Operation(summary = "Get User Profile Information", description = "Retrieves detailed profile information for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile information.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DashboardProfileInfoDTO.class))),

            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<DashboardProfileInfoDTO> getProfileInfo(
            @Parameter(in = ParameterIn.PATH, description = "The ID of the user whose profile information is being retrieved", required = true, example = "1")
            Long userId
    );
}
