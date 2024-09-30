package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginUserDTO(
        @Email(message = "Email isn't right") @NotNull(message = "Field email is required") String email,
        @NotNull(message = "Field password is required") @Size(min = 8, message = "Field password shoud have at least 8 characters") String password
) {

}
