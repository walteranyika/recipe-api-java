package com.walter.recipeapi.controllers;

import com.walter.recipeapi.exceptions.NoSuchReviewException;
import com.walter.recipeapi.exceptions.RecipeNotFoundException;
import com.walter.recipeapi.models.Recipe;
import com.walter.recipeapi.models.Review;
import com.walter.recipeapi.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reviewService.getReviewById(id));
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<?> getReviewsByRecipe(@PathVariable Long recipeId) {
        try {
            List<Review> reviewList = reviewService.getReviewsByRecipeId(recipeId);
            return ResponseEntity.ok(reviewList);
        } catch (RecipeNotFoundException | NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getReviewsByUser(@PathVariable String username) {
        try {
            List<Review> reviewList = reviewService.getReviewByUsername(username);
            return ResponseEntity.ok(reviewList);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<?> postNewReview(@RequestBody Review review, @PathVariable Long recipeId) {
        try {
            Recipe recipe = reviewService.postNewReview(review, recipeId);
            return ResponseEntity.created(recipe.getLocationUri()).body(recipe);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> postNewReview(@PathVariable Long reviewId) {
        try {
            Review review = reviewService.deleteReviewBy(reviewId);
            return ResponseEntity.ok(review);
        } catch (NoSuchReviewException e) {
            throw new RuntimeException(e);
        }
    }
}

