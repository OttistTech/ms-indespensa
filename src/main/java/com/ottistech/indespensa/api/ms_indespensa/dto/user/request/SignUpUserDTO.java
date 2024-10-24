package com.ottistech.indespensa.api.ms_indespensa.dto.user.request;

import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;

public record SignUpUserDTO(
        @NotNull(message = "Field type is required")
        @Schema(description = "Type of user (e.g., 'ADMIN', 'PERSONAL', 'BUSINESS')", example = "PERSONAL")
        String type,

        @NotNull(message = "Field name is required")
        @Schema(description = "User's full name", example = "Pedro Henrique")
        String name,

        @Schema(description = "Birth date of the user in ISO format", example = "2024-10-19T23:40:20.627Z")
        Date birthDate,

        @Schema(description = "Type of enterprise if applicable", example = "startup")
        String enterpriseType,

        @Email(message = "Email isn't right")
        @NotNull(message = "Field email is required")
        @Schema(description = "User's email address", example = "pedro.henrique@example.com")
        String email,

        @NotNull(message = "Field password is required")
        @Size(min = 8, message = "Field password should have at least 8 characters")
        @Schema(description = "User's password (minimum 8 characters)", example = "P@ssw0rd123")
        String password,

        @NotNull(message = "Field cep is required")
        @Size(min = 8, max = 8, message = "Field cep should have exactly 8 characters")
        @Schema(description = "Postal code (CEP) of the user's address (maximum 8 characters)", example = "12345678")
        String cep,

        @Min(value = 1, message = "Field addressNumber should have one character")
        @Max(value = 9999, message = "Field addressNumber should have max 4 characters")
        @Schema(description = "Street number of the user's address (maximum 4 characters)", example = "123")
        Integer addressNumber,

        @NotNull(message = "Field street is required")
        @Schema(description = "Street name of the user's address", example = "Main Street")
        String street,

        @NotNull(message = "Field city is required")
        @Schema(description = "City of the user's address", example = "New York")
        String city,

        @NotNull(message = "Field state is required")
        @Schema(description = "State of the user's address", example = "NJ")
        String state
) {

    public User toUser() {
        return new User(
                this.type,
                this.name,
                this.birthDate,
                this.enterpriseType,
                this.email,
                this.password
        );
    }

    public Cep toCep() {
        return new Cep(
                this.cep,
                this.street,
                this.city,
                this.state
        );
    }

    public Address toAddress(User user, Cep cep) {
        return new Address(
                user,
                cep,
                this.addressNumber
        );
    }

}
