package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "completed_recipes", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "recipe_id"})})
public class CompletedRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completed_recipe_id")
    private Long completedRecipeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    private Recipe recipe;

    @Column(name = "date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "num_stars", nullable = false)
    @Min(value = 0, message = "Field numStars must be 0 => numStars <= 5")
    @Max(value = 5, message = "Field numStars must be 0 => numStars <= 5")
    @Check(constraints = "num_stars >= 1 AND num_stars <= 5")
    private Integer numStars;

    public CompletedRecipe(User user, Recipe recipe, Integer numStars) {
        this.user = user;
        this.recipe = recipe;
        this.numStars = numStars;
        this.date = LocalDateTime.now();
    }
}
