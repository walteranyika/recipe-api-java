package com.walter.recipeapi.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int minutesToMake;
    @Column(nullable = false)
    private Integer difficultyRating;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey)
    private Collection<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey)
    private Collection<Step> steps = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey)
    private Collection<Review> reviews;

    @Transient
    @JsonIgnore
    private URI locationUri;

    public void setDifficultyRating(int difficultyRating){
        if (difficultyRating<0 | difficultyRating>10){
            throw new IllegalStateException("Difficulty rating  should be between 0 and 10");
        }
        this.difficultyRating = difficultyRating;
    }

    public void validate() throws IllegalStateException{
        if (ingredients.isEmpty()){
            throw  new IllegalStateException("You have to have at least one ingredient for this recipe");
        }
        else if(steps.isEmpty()){
            throw new IllegalStateException("You have to show a few steps on how dto cook");
        }
    }

    public void generateLocationURI(){
        locationUri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/recipes/")
                .path(String.valueOf(id))
                .toUriString());
    }
}
