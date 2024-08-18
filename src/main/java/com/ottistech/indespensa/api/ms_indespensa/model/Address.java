package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
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
@Table(name = "addresses", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "cep", "street", "address_number"}))
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "cep", nullable = false, length = 20)
    @NotNull(message = "Field cep is required")
    private String cep;

    @Column(name = "address_number")
    private Integer addressNumber;

    @Column(name = "street", nullable = false, length = 255)
    @NotNull(message = "Field street is required")
    private String street;

    @Column(name = "city", nullable = false, length = 255)
    @NotNull(message = "Field city is required")
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    @NotNull(message = "Field state is required")
    private String state;
}
