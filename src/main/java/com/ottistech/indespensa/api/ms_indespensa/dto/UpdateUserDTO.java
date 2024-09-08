package com.ottistech.indespensa.api.ms_indespensa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDTO(
        @NotNull(message = "Field name is required") String name,
        @Email(message = "Email isn't right") @NotNull(message = "Field email is required") String email,
        @NotNull(message = "Field password is required") String password,
        @NotNull(message = "Field cep is required") String cep,
        @NotNull(message = "Field addressNumber is required") Integer addressNumber,
        @NotNull(message = "Field street is required") String street,
        @NotNull(message = "Field city is required") String city,
        @NotNull(message = "Field state is required") String state
) {
}
