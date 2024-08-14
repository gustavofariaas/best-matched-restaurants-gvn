package com.assessment.controller;

import com.assessment.dto.RestaurantRequestDTO;
import com.assessment.service.BestMatchedRestaurantsGvnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BestMatchedRestaurantsGvnController {

    @Autowired
    private BestMatchedRestaurantsGvnService service;

    @GetMapping("/getListOfMatchedRestaurants")
    public ResponseEntity<Object> getListOfMatchedRestaurants(@Valid RestaurantRequestDTO restaurantRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> mapError = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                mapError.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(service.getRestaurantInformation(restaurantRequestDTO));
    }
}
