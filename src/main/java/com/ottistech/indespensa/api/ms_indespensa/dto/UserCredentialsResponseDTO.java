package com.ottistech.indespensa.api.ms_indespensa.dto;

public record UserCredentialsResponseDTO(
    Long userId,
    String type,
    String name,
    String email,
    String password,
    String enterpriseType,
    Boolean isPremium
) {

}
