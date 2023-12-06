package com.walter.recipeapi.services;

import com.walter.recipeapi.exceptions.RecipeNotFoundException;
import com.walter.recipeapi.models.Recipe;
import com.walter.recipeapi.repos.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepo recipeRepo;


    @Transactional
    public Recipe createRecipe(Recipe recipe) throws IllegalStateException {
        recipe.validate();
        recipe = recipeRepo.save(recipe);
        recipe.generateLocationURI();
        return recipe;
    }

    public Recipe getRecipeById(Long id) throws RecipeNotFoundException {
        Optional<Recipe> recipeOptional = recipeRepo.findById(id);
        if (recipeOptional.isEmpty()) {
            throw new RecipeNotFoundException("No recipe exists with id " + id);
        }
        Recipe recipe = recipeOptional.get();
        recipe.generateLocationURI();
        return recipe;
    }

    public List<Recipe> getRecipeByName(String name) throws RecipeNotFoundException {
        List<Recipe> recipeList = recipeRepo.findRecipeByNameContainingIgnoreCase(name);
        if (recipeList.isEmpty()) {
            throw new RecipeNotFoundException("No recipes could be found with such a name : " + name);
        }
        return recipeList;
    }

    public List<Recipe> getAllRecipes() throws RecipeNotFoundException {
        List<Recipe> recipeList = recipeRepo.findAll();
        if (recipeList.isEmpty()) {
            throw new RecipeNotFoundException("No recipe found. You can add yor sumptuous one here.");
        }
        return recipeList;
    }

    @Transactional
    public Recipe deleteById(Long id) throws RecipeNotFoundException {
        try {
            Recipe recipe = getRecipeById(id);
            recipeRepo.deleteById(id);
            return recipe;
        } catch (Exception e) {
            throw new RecipeNotFoundException(e.getMessage() + ": Could not delete the recipe");
        }
    }

    @Transactional
    public Recipe updateRecipe(Recipe recipe, boolean forceCheck) throws RecipeNotFoundException {
        try {
            if (forceCheck) {
                getRecipeById(recipe.getId());
            }
            recipe.validate();
            Recipe updatedRecipe = recipeRepo.save(recipe);
            updatedRecipe.generateLocationURI();
            return updatedRecipe;
        } catch (RecipeNotFoundException e) {
            throw new RecipeNotFoundException("The recipe you passed in did not match any recipes in our collection. Please check the iD again");
        }
    }


}
