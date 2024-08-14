package com.assessment.dto;

public class RestaurantDTO {

    public RestaurantDTO(String restaurantName, Integer customerRating, Integer distance, Integer price, String cuisineName) {
        this.restaurantName = restaurantName;
        this.customerRating = customerRating;
        this.distance = distance;
        this.price = price;
        this.cuisineName = cuisineName;
    }

    public RestaurantDTO() {
    }

    private String restaurantName;
    private Integer customerRating;
    private Integer distance;
    private Integer price;
    private String cuisineName;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }
}
