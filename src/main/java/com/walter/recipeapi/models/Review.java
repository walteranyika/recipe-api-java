package com.walter.recipeapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private int rating;

    @NotNull
    private  String description;

    public void setRating(int rating){
        if (rating<=0 || rating>=10){
            throw new IllegalStateException("Rating must be between 0 and 10.");
        }
        this.rating=rating;
    }
}
