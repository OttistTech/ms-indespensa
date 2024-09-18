package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import jakarta.validation.constraints.*;

public record UpdateUserDTO(
        @NotNull(message = "Field name is required") String name,
        @Email(message = "Email isn't right") @NotNull(message = "Field email is required") String email,
        @NotNull(message = "Field password is required") @Size(min = 8, message = "Field password shoud have at least 8 characters") String password,
        @NotNull(message = "Field cep is required") @Size(min = 8, max = 8, message = "Field cep should have exactly 8 characters") String cep,
        @NotNull(message = "Field addressNumber is required") @Min(value = 1, message = "Field addressNumber should be at least 1 (>= 1)") @Max(value = 4, message = "Field addressNumber should be less than 5 (< 4)") Integer addressNumber,
        @NotNull(message = "Field street is required") String street,
        @NotNull(message = "Field city is required") String city,
        @NotNull(message = "Field state is required") String state
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
