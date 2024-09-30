package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "cep_id", "address_number"}))
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cep_id", referencedColumnName = "cep_id")
    private Cep cep;

    @Column(name = "address_number")
    private Integer addressNumber;

    public Address(User user, Cep cep, Integer addressNumber) {
        this.user = user;
        this.cep = cep;
        this.addressNumber = addressNumber;
    }
}
