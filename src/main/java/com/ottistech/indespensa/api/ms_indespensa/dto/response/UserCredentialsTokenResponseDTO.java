package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.User;

public record UserCredentialsTokenResponseDTO(
        Long userId,
        String type,
        String name,
        String email,
        String password,
        String enterpriseType,
        Boolean isPremium,
        String token
) {

    public static UserCredentialsTokenResponseDTO fromUser(User user, String token) {
        return new UserCredentialsTokenResponseDTO(
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
