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
@Table(name = "list_items")
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_item_id")
    private Long listItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @NotNull(message = "Field amount is required")
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    public ShopItem(User user, Product product, Integer amount) {
        this.user = user;
        this.product = product;
        this.amount = amount;
    }

    public PantryItem toPantryItem() {
        return new PantryItem(
            this.getUser(),
            this.getProduct()
        );
    }
}
