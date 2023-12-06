package com.walter.recipeapi.controllers;

import com.walter.recipeapi.exceptions.RecipeNotFoundException;
import com.walter.recipeapi.models.Recipe;
import com.walter.recipeapi.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @PostMapping
    public ResponseEntity<?> createNewRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe savedRecipe = recipeService.createRecipe(recipe);
            return ResponseEntity.created(savedRecipe.getLocationUri()).body(savedRecipe);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        try {
            Recipe savedRecipe = recipeService.getRecipeById(id);
            return ResponseEntity.ok(savedRecipe);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> search(@PathVariable String search) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeByName(search));
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> search(@PathVariable Long id) {
        try {
            Recipe recipe = recipeService.deleteById(id);
            Map<String, String> map = new HashMap<>();
            map.put("recipeId", recipe.getId().toString());
            map.put("title", recipe.getName());
            map.put("message", "Deleted successfully");
            return ResponseEntity.ok(map);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PatchMapping
    public ResponseEntity<?> update(@RequestBody Recipe recipe) {
        try {
            Recipe updatedRecipe = recipeService.updateRecipe(recipe, true);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
