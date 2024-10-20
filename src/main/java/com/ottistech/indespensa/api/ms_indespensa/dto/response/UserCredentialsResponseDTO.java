package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing user's credentials and authentication token if user is admin")
public record UserCredentialsResponseDTO(
        @Schema(description = "User's unique identifier", example = "1")
        Long userId,

        @Schema(description = "Type of user (e.g., 'ADMIN', 'PERSONAL', 'BUSINESS')", example = "PERSONAL")
        String type,

        @Schema(description = "User's full name", example = "Pedro Henrique")
        String name,

        @Schema(description = "User's email address", example = "pedro.henrique@example.com")
        String email,

        @Schema(description = "User's password (hashed)", example = "$2a$10$...")
        String password,

        @Schema(description = "Enterprise type (if user is BUSINESS)", example = "Foods")
        String enterpriseType,

        @Schema(description = "Flag indicating if the user has a premium account", example = "true")
        Boolean isPremium,

        @Schema(description = "JWT authentication token (prefixed with 'Bearer ')", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {

    public static UserCredentialsResponseDTO fromUser(User user, String token) {
        return new UserCredentialsResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getEnterpriseType(),
                user.getIsPremium(),
                token == null ? null : "Bearer " + token
        );
    }

}
