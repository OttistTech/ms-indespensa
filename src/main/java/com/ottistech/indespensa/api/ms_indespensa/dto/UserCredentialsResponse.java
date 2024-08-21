package com.ottistech.indespensa.api.ms_indespensa.dto;

import java.util.Date;

public record UserCredentialsResponse(
    Long userId,
    String type,
    String name,
    Date birthDate,
    String enterpriseType,
    Boolean isPremium
) {

}
