package com.ottistech.indespensa.api.ms_indespensa.dto.user.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Full information about a user, including personal and account details.")
public record UserFullInfoResponseDTO(
        @Schema(description = "Unique identifier of the user.", example = "12345")
        Long userId,

        @Schema(description = "Type of the user, such as ADMIN, PERSONAL or BUSINESS.", example = "BUSINESS")
        String type,

        @Schema(description = "Full name of the user.", example = "Pedro Henrique")
        String name,

        @Schema(description = "User's date of birth.", example = "1990-01-15", type = "string", format = "date")
        Date birthDate,

        @Schema(description = "Type of enterprise if the user is an enterprise.", example = "Small Business")
        String enterpriseType,

        @Schema(description = "User's email address.", example = "pedro@example.com")
        String email,

        @Schema(description = "User's password (hashed)", example = "$2a$10$...")
        String password,

        @Schema(description = "Postal code of the user's address.", example = "12345678")
        String cep,

        @Schema(description = "Street number of the user's address.", example = "1234")
        Integer addressNumber,

        @Schema(description = "Street name of the user's address.", example = "Main Street")
        String street,

        @Schema(description = "City of the user's address.", example = "New York")
        String city,

        @Schema(description = "State of the user's address.", example = "NY")
        String state,

        @Schema(description = "Indicates if the user has a premium account.", example = "true")
        Boolean isPremium
) {

    public static UserFullInfoResponseDTO fromUserCepAddress(User user, Cep cep, Address address) {
        return new UserFullInfoResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getBirthDate(),
                user.getEnterpriseType(),
                user.getEmail(),
                user.getPassword(),
                cep.getCepId(),
                address.getAddressNumber(),
                cep.getStreet(),
                cep.getCity(),
                cep.getState(),
                user.getIsPremium()
        );
    }
}
