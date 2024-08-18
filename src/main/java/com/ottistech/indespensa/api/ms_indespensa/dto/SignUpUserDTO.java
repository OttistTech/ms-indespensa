package com.ottistech.indespensa.api.ms_indespensa.dto;

import com.ottistech.indespensa.api.ms_indespensa.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpUserDTO(
        @NotNull(message = "Field type is required") String type,
        @NotNull(message = "Field name is required") String name,
        String enterpriseType,
        @Email(message = "Email isn't right") @NotNull(message = "Field email is required") String email,
        @NotNull(message = "Field password is required") String password
) {

    public User toUser() {
        return new User(
                this.type,
                this.name,
                this.enterpriseType,
                this.email,
                this.password
        );
    }
}
