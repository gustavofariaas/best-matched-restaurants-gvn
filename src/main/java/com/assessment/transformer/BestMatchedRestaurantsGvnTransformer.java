package com.assessment.transformer;

import com.assessment.dto.RestaurantDTO;
import com.assessment.projection.RestaurantProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BestMatchedRestaurantsGvnTransformer {

    public List<RestaurantDTO> transform(List<RestaurantProjection> projection) {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        for(RestaurantProjection pr : projection) {
            RestaurantDTO restaurantDTO = new RestaurantDTO(
                    pr.getRestaurantName(),
                    pr.getCustomerRating(),
                    pr.getDistance(),
                    pr.getPrice(),
                    pr.getCuisineName()
            );
            restaurantDTOList.add(restaurantDTO);
        }
        return restaurantDTOList;
    }
}
