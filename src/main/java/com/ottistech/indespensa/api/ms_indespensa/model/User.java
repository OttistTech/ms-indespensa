package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "'user'")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @NotNull(message = "Field type is required")
    private String type;

    @NotNull(message = "Field name is required")
    private String name;

    @Column(name = "enterprise_type")
    private String enterpriseType;

    @Email(message = "Email isn't right")
    @NotNull(message = "Field email is required")
    private String email;

    @NotNull(message = "Field password is required")
    private String password;

    @NotNull(message = "Field cep is required")
    private String cep;

    @Column(name = "address_number")
    private Integer addressNumber;

    @NotNull(message = "Field street is required")
    private String street;

    @NotNull(message = "Field city is required")
    private String city;

    @NotNull(message = "Field state is required")
    private String state;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "deactivated_at")
    private Date deactivatedAt;

    @Column(name = "is_premium")
    private boolean isPremium;
}
