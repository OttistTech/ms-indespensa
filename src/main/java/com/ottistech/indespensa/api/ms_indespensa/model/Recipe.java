package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @NotNull(message = "Field title is required")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotNull(message = "Field description is required")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Field level is required")
    @Column(name = "level", nullable = false, length = 20)
    private String level;

    @NotNull(message = "Field preparationTime is required")
    @Column(name = "preparation_time", nullable = false)
    private Integer preparationTime;

    @NotNull(message = "Field preparationMethod is required")
    @Column(name = "preparation_method", nullable = false, columnDefinition = "TEXT")
    private String preparationMethod;

    @Column(name = "is_shared")
    private Boolean isShared;

//    @OneToMany(mappedBy = "recipeId")
//    private List<RecipeIngredient> ingredients;
//
//    @OneToMany(mappedBy = "recipeId")
//    private List<DoneRecipe> doneRecipes;
}
