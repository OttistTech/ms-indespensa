package com.ottistech.indespensa.api.ms_indespensa.dto.user.request;

import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO for updating user information.")
public record UpdateUserDTO(

        @Schema(description = "The user's full name.", example = "Pedro Henrique")
        @NotNull(message = "Field name is required")
        String name,

        @Schema(description = "The user's email address.", example = "pedrohenrique@example.com")
        @Email(message = "Email isn't right")
        @NotNull(message = "Field email is required")
        String email,

        @Schema(description = "The user's password. Must be at least 8 characters.", example = "mypassword123")
        @NotNull(message = "Field password is required")
        @Size(min = 8, message = "Field password shoud have at least 8 characters")
        String password,

        @Schema(description = "The user's postal code (CEP). Must be exactly 8 characters.")
        @NotNull(message = "Field cep is required")
        @Size(min = 8, max = 8, message = "Field cep should have exactly 8 characters")
        String cep,

        @Schema(description = "The user's address number. Should be between 1 and 9999.", example = "123")
        @NotNull(message = "Field addressNumber is required")
        @Min(value = 1, message = "Field addressNumber should be at least 1 (>= 1)")
        @Max(value = 9999, message = "Field addressNumber should be less than 5 (< 4)")
        Integer addressNumber,

        @Schema(description = "The street of the user's address.", example = "Main Street")
        @NotNull(message = "Field street is required")
        String street,

        @Schema(description = "The city of the user's address.", example = "New York")
        @NotNull(message = "Field city is required")
        String city,

        @Schema(description = "The state of the user's address.", example = "NJ")
        @NotNull(message = "Field state is required")
        String state
) {

    public Cep toCep() {
        return new Cep(
                this.cep,
                this.street,
                this.city,
                this.state
        );
    }
}
