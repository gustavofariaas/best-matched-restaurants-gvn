package com.assessment.entity;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    @Column
    private String name;
    @Column
    private Integer customer_rating;
    @Column
    private Integer distance;
    @Column
    private Integer price;
    @Column
    private Long cuisine_id;

    public Long getCuisineId() {
        return cuisine_id;
    }

    public void setCuisineId(Long cuisine) {
        this.cuisine_id = cuisine;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCustomerRating() {
        return customer_rating;
    }

    public void setCustomerRating(Integer customer_rating) {
        this.customer_rating = customer_rating;
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
