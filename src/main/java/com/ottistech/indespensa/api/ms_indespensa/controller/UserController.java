package com.ottistech.indespensa.api.ms_indespensa.controller;

import com.ottistech.indespensa.api.ms_indespensa.dto.request.LoginUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.SignUpUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.request.UpdateUserDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserCredentialsResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.dto.response.UserFullInfoResponseDTO;
import com.ottistech.indespensa.api.ms_indespensa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Operations related to User management")
public class UserController {

    private final UserService userService;

    // TODO: verify why Swagger is adding some Status Code that doesn't make sense in some endpoints

    @PostMapping("/signup")
    @Operation(summary = "Register a new user", description = "Sign up a new user by providing the required information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentialsResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "409", description = "Email already in use",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "410", description = "Email already deactivated",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserCredentialsResponseDTO> registerUser(@RequestBody @Valid SignUpUserDTO signUpUserDTO) {

        UserCredentialsResponseDTO userCredentialsResponse = userService.signUpUser(signUpUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCredentialsResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Log in an existing user by providing valid credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentialsResponseDTO.class))),

            @ApiResponse(responseCode = "401", description = "Provided user password is wrong",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "Provided user credentials doesn't exists",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "410", description = "User already deactivated",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))

    })
    public ResponseEntity<UserCredentialsResponseDTO> loginUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {

        UserCredentialsResponseDTO userCredentials = userService.getUserCredentials(loginUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userCredentials);
    }

    @DeleteMapping("/deactivation/{id}")
    @Operation(summary = "Deactivate a user", description = "Deactivates the user by his ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deactivated"),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "410", description = "User already deactivated",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deactivateUser(@PathVariable("id") Long userId) {

        userService.deactivateUserById(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user information", description = "Fetches either full or half user information depending on the 'full-info' parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(oneOf = {UserFullInfoResponseDTO.class, UserCredentialsResponseDTO.class}))}),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> getUserFullInfo(@PathVariable("id") Long userId,
                                             @RequestParam("full-info") boolean fullInfo) {

        if (fullInfo) {
            UserFullInfoResponseDTO userFullInfo = userService.getUserFullInfo(userId);
            return ResponseEntity.ok(userFullInfo);
        }

        UserCredentialsResponseDTO userHalfInfo = userService.getUserHalfInfo(userId);

        return ResponseEntity.ok(userHalfInfo);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Get all users' information", description = "Fetches full information of all users. Only accessible by admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserFullInfoResponseDTO.class))),

            @ApiResponse(responseCode = "401", description = "You can't access this resource",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "403", description = "You must be authenticated to access this resource",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<UserFullInfoResponseDTO>> getAllUsersFullInfo() {

        List<UserFullInfoResponseDTO> userFullInfoList = userService.getAllUsersFullInfo();

        return ResponseEntity.ok(userFullInfoList);
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Get one user's full information", description = "Fetches full information of a specific user. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserFullInfoResponseDTO.class))),

            @ApiResponse(responseCode = "401", description = "You can't access this resource",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "403", description = "You must be authenticated to access this resource"),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserFullInfoResponseDTO> getOneUserFullInfo(@PathVariable("id") Long userId) {

        UserFullInfoResponseDTO userFullInfo = userService.getUserFullInfo(userId);

        return ResponseEntity.ok(userFullInfo);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update user information", description = "Updates the user's information using the provided user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentialsResponseDTO.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserCredentialsResponseDTO> updateUser(@PathVariable("id") Long userId,
                                        @RequestBody @Valid UpdateUserDTO userDTO) {

        UserCredentialsResponseDTO userCredentials = userService.updateUser(userId, userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userCredentials);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Upgrade user to premium", description = "Upgrades the user to a premium account by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User upgraded to premium"),

            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "410", description = "User already is premium",
                    content = @Content(mediaType = "application/json")),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateUserBecomePremium(@PathVariable("id") Long userId) {

        userService.updateUserBecomePremium(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
