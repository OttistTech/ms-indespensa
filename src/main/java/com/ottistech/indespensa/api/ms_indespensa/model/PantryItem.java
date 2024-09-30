package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pantry_items")
public class PantryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pantry_item_id")
    private Long pantryItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @NotNull(message = "Field amount is required")
    @Column(name = "amount", nullable = false)
    private Integer amount = 0;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate = LocalDate.now();

    @Column(name = "is_active")
    private Boolean isActive = Boolean.FALSE;

    public PantryItem(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public PantryItem(User user, Product product, Integer amount, LocalDate validityDate) {
        this.user = user;
        this.product = product;
        this.amount = amount;
        this.validityDate = validityDate;
    }
}
