package com.walter.recipeapi.repos;

import com.walter.recipeapi.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findReviewByUsername(String username);
}
