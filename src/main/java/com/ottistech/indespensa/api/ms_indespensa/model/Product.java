package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "ean_code", length = 50, unique = true)
    private String eanCode;

    @NotNull(message = "Field name is required")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_id", referencedColumnName = "food_id")
    private Food foodId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category categoryId;

    @NotNull(message = "Field description is required")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private Brand brandId;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "type", length = 50)
    private String type;
}
