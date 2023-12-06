package com.walter.recipeapi.repos;

import com.walter.recipeapi.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    List<Recipe> findRecipeByNameContainingIgnoreCase(String name);
}
