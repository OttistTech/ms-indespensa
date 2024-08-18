package com.ottistech.indespensa.api.ms_indespensa.dto;

public record UserCredentialsResponse(
    Long userId,
    String type,
    String name,
    String enterpriseType,
    Boolean isPremium
) {

}
