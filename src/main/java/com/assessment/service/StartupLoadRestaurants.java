package com.assessment.service;

import com.assessment.entity.Cuisine;
import com.assessment.entity.Restaurant;
import com.assessment.exception.BestMatchedRestaurantsGvnException;
import com.assessment.persistance.CuisineRepository;
import com.assessment.persistance.RestaurantRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;


@Component
@Profile("!test")
public class StartupLoadRestaurants implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            List<Cuisine> cuisineDTOList = new CsvToBeanBuilder(new FileReader("src/main/resources/csv/cuisines.csv"))
                    .withType(Cuisine.class).build().parse();

            cuisineRepository.saveAll(cuisineDTOList);

            List<Restaurant> restaurantsDTOList = new CsvToBeanBuilder(new FileReader("src/main/resources/csv/restaurants.csv"))
                    .withType(Restaurant.class).build().parse();
            restaurantRepository.saveAll(restaurantsDTOList);


        } catch (Exception e) {
            throw new BestMatchedRestaurantsGvnException(e.getMessage(), e);
        }
    }
}