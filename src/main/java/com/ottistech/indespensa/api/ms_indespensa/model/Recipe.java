package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Recipe(User createdBy, String title, String description, String level, Integer preparationTime, String preparationMethod, Boolean isShared) {
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.level = level;
        this.preparationTime = preparationTime;
        this.preparationMethod = preparationMethod;
        this.isShared = isShared;
    }

}
