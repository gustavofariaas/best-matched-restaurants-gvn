package com.assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cuisine {

    @Id
    private Long id;
    @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuisineName() {
        return name;
    }

    public void setCuisineName(String cuisineName) {
        this.name = cuisineName;
    }
}
