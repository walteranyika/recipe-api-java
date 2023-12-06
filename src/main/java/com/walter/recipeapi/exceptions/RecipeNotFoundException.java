package com.walter.recipeapi.exceptions;

public class RecipeNotFoundException extends Exception {
    public RecipeNotFoundException() {
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }
}
