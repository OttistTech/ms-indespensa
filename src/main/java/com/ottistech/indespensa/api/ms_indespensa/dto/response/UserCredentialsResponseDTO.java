package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ottistech.indespensa.api.ms_indespensa.model.User;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record UserCredentialsResponseDTO(
        Long userId,
        String type,
        String name,
        String email,
        String password,
        String enterpriseType,
        Boolean isPremium,
        String token
) {

    public static UserCredentialsResponseDTO fromUser(User user, String token) {
        return new UserCredentialsResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getEnterpriseType(),
                user.getIsPremium(),
                token == null ? null : "Bearer " + token
        );
    }
}
