package com.ottistech.indespensa.api.ms_indespensa.dto.request;

import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import jakarta.validation.constraints.*;

import java.util.Date;

public record SignUpUserDTO(
        @NotNull(message = "Field type is required") String type,
        @NotNull(message = "Field name is required") String name,
        Date birthDate,
        String enterpriseType,
        @Email(message = "Email isn't right") @NotNull(message = "Field email is required") String email,
        @NotNull(message = "Field password is required") @Size(min = 8, message = "Field password shoud have at least 8 characters") String password,
        @NotNull(message = "Field cep is required") @Size(min = 8, max = 8, message = "Field cep should have exactly 8 characters") String cep,
        @Min(value = 1, message = "Field addressNumber should have one char") @Max(value = 9999, message = "Field addressNumber should have max 4 chars") Integer addressNumber,
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
