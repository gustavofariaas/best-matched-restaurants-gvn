package com.assessment.dto;

import jakarta.validation.constraints.*;

public class RestaurantRequestDTO {


    @Pattern(regexp = "^\\S.*$", message = "Name must not be blank or contain only spaces if provided")
    private String restaurantName;

    @Pattern(regexp = "^\\S.*$", message = "Name must not be blank or contain only spaces if provided")
    private String cuisine;

    @Min(value = 1, message = "Customer rating must be at least 1")
    @Max(value = 5, message = "Customer rating must be less than 5")
    private Integer customerRating;

    @Min(value = 1, message = "Distance must be at least 1")
    private Integer distance;

    @Min(value = 1, message = "Price must be at least 1")
    private Integer price;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {

        this.restaurantName = restaurantName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Integer getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Integer customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
