package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;

public record SignUpUserDTO(
        @NotNull(message = "Field type is required")
        @Schema(example = "individual", description = "Type of user (individual or business)")
        String type,

        @NotNull(message = "Field name is required")
        @Schema(example = "John Doe", description = "Full name of the user")
        String name,

        @Schema(example = "2024-10-19T23:40:20.627Z", description = "Birth date of the user in ISO format")
        Date birthDate,

        @Schema(example = "startup", description = "Type of enterprise if applicable")
        String enterpriseType,

        @Email(message = "Email isn't right")
        @NotNull(message = "Field email is required")
        @Schema(example = "john.doe@example.com", description = "Email address of the user")
        String email,

        @NotNull(message = "Field password is required")
        @Size(min = 8, message = "Field password should have at least 8 characters")
        @Schema(example = "P@ssw0rd123", description = "User's password (minimum 8 characters)")
        String password,

        @NotNull(message = "Field cep is required")
        @Size(min = 8, max = 8, message = "Field cep should have exactly 8 characters")
        @Schema(example = "12345678", description = "Postal code (CEP) of the user's address")
        String cep,

        @Min(value = 1, message = "Field addressNumber should have one char")
        @Max(value = 9999, message = "Field addressNumber should have max 4 chars")
        @Schema(example = "123", description = "Street number of the user's address")
        Integer addressNumber,

        @NotNull(message = "Field street is required")
        @Schema(example = "Main Street", description = "Street name of the user's address")
        String street,

        @NotNull(message = "Field city is required")
        @Schema(example = "New York", description = "City of the user's address")
        String city,

        @NotNull(message = "Field state is required")
        @Schema(example = "NY", description = "State of the user's address")
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
