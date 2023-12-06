package com.walter.recipeapi.exceptions;

public class NoSuchReviewException extends Exception{
    public NoSuchReviewException() {
    }

    public NoSuchReviewException(String message) {
        super(message);
    }
}
