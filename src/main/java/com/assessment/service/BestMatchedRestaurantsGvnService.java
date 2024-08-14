package com.assessment.service;

import com.assessment.dto.RestaurantDTO;
import com.assessment.dto.RestaurantRequestDTO;
import com.assessment.entity.Restaurant;
import com.assessment.persistance.RestaurantRepository;
import com.assessment.projection.RestaurantProjection;
import com.assessment.transformer.BestMatchedRestaurantsGvnTransformer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestMatchedRestaurantsGvnService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BestMatchedRestaurantsGvnTransformer transformer;

    public List<RestaurantDTO> getRestaurantInformation(RestaurantRequestDTO dto) {
        List<RestaurantProjection> listOfRestaurants = restaurantRepository
                .retrieveRestaurantInformation();
        if(CollectionUtils.isNotEmpty(listOfRestaurants)) {
            listOfRestaurants = filterResultBasedOnRequest(listOfRestaurants, dto);

            List<RestaurantDTO> transformedList = transformer.transform(listOfRestaurants);

            transformedList.sort(Comparator
                    .comparing(RestaurantDTO::getDistance).reversed()
                    .thenComparing(RestaurantDTO::getCustomerRating).reversed()
                    .thenComparing(RestaurantDTO::getPrice)
                    .thenComparing(RestaurantDTO::getRestaurantName)
            );

            return transformedList.stream().limit(5).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    private List<RestaurantProjection> filterResultBasedOnRequest(List<RestaurantProjection> listOfRestaurants, RestaurantRequestDTO dto) {
        return listOfRestaurants.stream()
                .filter(
                        restaurant -> ObjectUtils.isEmpty(dto.getRestaurantName()) || restaurant.getRestaurantName().toLowerCase().contains(dto.getRestaurantName().toLowerCase())
                )
                .filter(
                        restaurant -> ObjectUtils.isEmpty(dto.getCustomerRating()) || restaurant.getCustomerRating() >= dto.getCustomerRating()
                )
                .filter(
                        restaurant -> ObjectUtils.isEmpty(dto.getDistance()) || restaurant.getDistance() <= dto.getDistance()
                )
                .filter(
                        restaurant -> ObjectUtils.isEmpty(dto.getPrice()) || restaurant.getPrice() <= dto.getPrice()
                )
                .filter(
                        restaurant -> ObjectUtils.isEmpty(dto.getCuisine()) || restaurant.getCuisineName().toLowerCase().contains(dto.getCuisine().toLowerCase())
                )
                .collect(Collectors.toList());
    }
}
