package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.CompletedRecipe;
import com.ottistech.indespensa.api.ms_indespensa.model.Recipe;
import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompletedRecipeRepository extends JpaRepository<CompletedRecipe, Long> {

    Optional<CompletedRecipe> findByUserAndRecipe(User user, Recipe recipe);
}
