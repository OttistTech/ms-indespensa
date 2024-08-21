package com.ottistech.indespensa.api.ms_indespensa.dto;

import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record SignUpUserDTO(
        @NotNull(message = "Field type is required") String type,
        @NotNull(message = "Field name is required") String name,
        @NotNull(message = "Field birthDate is required") Date birthDate,
        String enterpriseType,
        @Email(message = "Email isn't right") @NotNull(message = "Field email is required") String email,
        @NotNull(message = "Field password is required") String password,

        @NotNull(message = "Field cep is required") String cep,
        Integer addressNumber,
        @NotNull(message = "Field street is required") String street,
        @NotNull(message = "Field city is required") String city,
        @NotNull(message = "Field state is required") String state
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

    public Address toAddress(User user) {
        return new Address(
                user,
                this.cep,
                this.addressNumber,
                this.street,
                this.city,
                this.state
        );
    }
}
