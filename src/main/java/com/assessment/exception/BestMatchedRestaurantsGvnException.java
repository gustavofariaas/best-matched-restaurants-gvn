package com.assessment.exception;

public class BestMatchedRestaurantsGvnException extends RuntimeException{
    public BestMatchedRestaurantsGvnException(String message) {
        super(message);
    }
    public BestMatchedRestaurantsGvnException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
