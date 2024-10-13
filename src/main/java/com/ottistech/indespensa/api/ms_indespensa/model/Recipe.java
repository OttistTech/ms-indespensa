package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @NotNull(message = "Field title is required")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotNull(message = "Field description is required")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Field difficulty is required")
    @Column(name = "level", nullable = false)
    private String level;

    @NotNull(message = "Field preparationTime is required")
    @Column(name = "preparation_time", nullable = false)
    @Min(value = 1, message = "Preparation time must be at least 1 minute")
    @Max(value = 1440, message = "Preparation time cannot exceed 1440 minutes")
    private Integer preparationTime;

    @NotNull(message = "Field preparationMethod is required")
    @Column(name = "preparation_method", nullable = false, columnDefinition = "TEXT")
    private String preparationMethod;

    @Column(name = "is_shared")
    private Boolean isShared;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<CompletedRecipe> completedRecipes;

    public Recipe(User createdBy, String title, String description, String level, Integer preparationTime, String preparationMethod, Boolean isShared, String imageUrl) {
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.level = level;
        this.preparationTime = preparationTime;
        this.preparationMethod = preparationMethod;
        this.isShared = isShared;
        this.imageUrl = imageUrl;
    }

}
