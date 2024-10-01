package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
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
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    private Long recipeIngredientId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_food_id", referencedColumnName = "food_id")
    private Food ingredientFood;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "is_essential")
    private Boolean isEssential;

    public RecipeIngredient(Recipe recipe, Food ingredientFood, BigDecimal amount, String unit, Boolean isEssential) {
        this.recipe = recipe;
        this.ingredientFood = ingredientFood;
        this.amount = amount;
        this.unit = unit;
        this.isEssential = isEssential;
    }
}
