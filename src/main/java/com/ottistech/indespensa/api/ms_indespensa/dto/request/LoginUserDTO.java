package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginUserDTO(
        @Schema(description = "The user's email address. Must be a valid email format.", example = "user@example.com")
        @Email(message = "Email isn't right")
        @NotNull(message = "Field email is required")
        String email,

        @Schema(description = "The user's password. Must be at least 8 characters long.", example = "strongpassword")
        @NotNull(message = "Field password is required")
        @Size(min = 8, message = "Field password should have at least 8 characters")
        String password
) {

}
