package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "Field type is required")
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @NotNull(message = "Field name is required")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "enterprise_type", length = 100)
    private String enterpriseType = null;

    @Email(message = "Email isn't right")
    @NotNull(message = "Field email is required")
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @NotNull(message = "Field password is required")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt = null;

    @Column(name = "is_premium")
    private Boolean isPremium = Boolean.FALSE;

    public User(String type, String name, String enterpriseType, String email, String password) {
        this.type = type;
        this.name = name;
        this.enterpriseType = enterpriseType;
        this.email = email;
        this.password = password;
    }
}
