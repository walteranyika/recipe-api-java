package com.walter.recipeapi.services;

import com.walter.recipeapi.exceptions.NoSuchReviewException;
import com.walter.recipeapi.exceptions.RecipeNotFoundException;
import com.walter.recipeapi.models.Recipe;
import com.walter.recipeapi.models.Review;
import com.walter.recipeapi.repos.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    RecipeService recipeService;

    public Review getReviewById(Long id) throws NoSuchReviewException {
        Optional<Review> review = reviewRepo.findById(id);

        if (review.isEmpty()) {
            throw new NoSuchReviewException("Review could not be found");
        }
        return review.get();
    }

    public List<Review> getReviewsByRecipeId(Long recipeId) throws NoSuchReviewException, RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        ArrayList<Review> reviews = new ArrayList<>(recipe.getReviews());
        if (reviews.isEmpty()) {
            throw new NoSuchReviewException("There are no reviews for this recipe");
        }
        return reviews;
    }

    public List<Review> getReviewByUsername(String username) throws NoSuchReviewException {
        List<Review> reviews = reviewRepo.findReviewByUsername(username);
        if (reviews.isEmpty()) {
            throw new NoSuchReviewException("There are no reviews for this username: " + username);
        }
        return reviews;
    }

    public Recipe postNewReview(Review review, Long recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.getReviews().add(review);
        recipeService.updateRecipe(recipe, false);
        return recipe;
    }

    public Review updateReviewById(Review reviewToUpdate) throws NoSuchReviewException {
        try {
            Review review = getReviewById(reviewToUpdate.getId());
        } catch (NoSuchReviewException e) {
            throw new NoSuchReviewException("The review could not be found");
        }
        reviewRepo.save(reviewToUpdate);
        return reviewToUpdate;
    }

    public Review deleteReviewBy(Long reviewId) throws NoSuchReviewException {
        try {
            Review review = getReviewById(reviewId);
            reviewRepo.deleteById(reviewId);
            return  review;
        } catch (NoSuchReviewException e) {
            throw new NoSuchReviewException("The review could not be found");
        }
    }
}
