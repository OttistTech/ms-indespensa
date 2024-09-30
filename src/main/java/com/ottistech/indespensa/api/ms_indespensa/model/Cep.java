package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cep")
public class Cep {

    @Id
    @Column(name = "cep_id", length = 20)
    private String cepId;

    @NotNull(message = "Field street is required")
    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @NotNull(message = "Field city is required")
    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @NotNull(message = "Field state is required")
    @Column(name = "state", nullable = false, length = 255)
    private String state;
}
