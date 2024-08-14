package com.assessment.persistance;

import com.assessment.dto.RestaurantDTO;
import com.assessment.entity.Restaurant;
import com.assessment.projection.RestaurantProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<RestaurantProjection> retrieveRestaurantInformation();

}
