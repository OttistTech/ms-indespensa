package com.ottistech.indespensa.api.ms_indespensa.controller.contract;

import com.ottistech.indespensa.api.ms_indespensa.dto.user.request.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.user.request.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.user.request.UpdateUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.user.response.UserCredentialsResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.user.response.UserFullInfoResponseDTO;
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

@Tag(name = "User", description = "Endpoints related to User management")
public interface UserContract {

    @Operation(summary = "Register a new user", description = "Sign up a new user by providing the required information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentialsResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "409", description = "Email already in use",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<UserCredentialsResponseDTO> registerUser(
            @Parameter(description = "User registration details.", required = true)
            SignUpUserDTO signUpUserDTO
    );

    @Operation(summary = "Login a user", description = "Log in an existing user by providing valid credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentialsResponseDTO.class))),

            @ApiResponse(responseCode = "401", description = "Provided user password is wrong",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "Provided user credentials doesn't exists",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "410", description = "User already deactivated",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<UserCredentialsResponseDTO> loginUser(
            @Parameter(description = "User login details.", required = true)
            LoginUserDTO loginUserDTO
    );

    @Operation(summary = "Deactivate a user", description = "Deactivates the user by his ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deactivated"),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "410", description = "User already deactivated",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Void> deactivateUser(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user to deactivate.", example = "1234", required = true)
            Long userId
    );

    @Operation(summary = "Get user information", description = "Fetches either full or half user information depending on the 'full-info' parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(oneOf = {UserFullInfoResponseDTO.class, UserCredentialsResponseDTO.class}))}),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<?> getUserFullInfo(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user to retrieve information for.", example = "1234", required = true)
            Long userId,

            @Parameter(in = ParameterIn.QUERY, description = "Flag indicating whether to return full user information.", required = true)
            boolean fullInfo
    );

    @Operation(summary = "Get all users' information", description = "Fetches full information of all users. Only accessible by admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserFullInfoResponseDTO.class))),

            @ApiResponse(responseCode = "401", description = "You can't access this resource",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "403", description = "You must be authenticated to access this resource",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<List<UserFullInfoResponseDTO>> getAllUsersFullInfo();

    @Operation(summary = "Get one user's full information", description = "Fetches full information of a specific user. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserFullInfoResponseDTO.class))),

            @ApiResponse(responseCode = "401", description = "You can't access this resource",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "403", description = "You must be authenticated to access this resource",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<UserFullInfoResponseDTO> getOneUserFullInfo(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user to retrieve information for.", example = "1234", required = true)
            Long userId
    );

    @Operation(summary = "Update user information", description = "Updates the user's information using the provided user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentialsResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "409", description = "Email is already in use by another user",
                    content = @Content(mediaType = "application/json")
            )
    })
    ResponseEntity<UserCredentialsResponseDTO> updateUser(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user to updated.", example = "1234", required = true)
            Long userId,

            @Parameter(description = "New details to update for the user.", required = true)
            UpdateUserDTO userDTO
    );

    @Operation(summary = "Upgrade/Cancel user to premium", description = "Upgrades the user to a premium account by their ID or cancel their premium account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User upgrated to premium or cancel his premium account"),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<Void> updateUserSwitchPremium(
            @Parameter(in = ParameterIn.PATH, description = "ID of the user to be upgraded to premium or cancel his premium account.", example = "1234", required = true)
            Long userId
    );

}
