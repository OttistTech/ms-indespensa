package com.ottistech.indespensa.api.ms_indespensa.dto.response;

import com.ottistech.indespensa.api.ms_indespensa.model.User;

public record UserCredentialsResponseDTO(
    Long userId,
    String type,
    String name,
    String email,
    String password,
    String enterpriseType,
    Boolean isPremium
) {

    public static UserCredentialsResponseDTO fromUser(User user) {
        return new UserCredentialsResponseDTO(
                user.getUserId(),
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getEnterpriseType(),
                user.getIsPremium()
        );
    }

}
