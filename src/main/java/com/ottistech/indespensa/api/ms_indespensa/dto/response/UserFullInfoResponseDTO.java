package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.Address;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import com.ottistech.indespensa.api.ms_indespensa.model.User;

import java.util.Date;

public record UserFullInfoResponseDTO(
        Long userId,
        String type,
        String name,
        Date birthDate,
        String enterpriseType,
        String email,
        String password,
        String cep,
        Integer addressNumber,
        String street,
        String city,
        String state,
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
