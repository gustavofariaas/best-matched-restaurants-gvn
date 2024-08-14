package com.assessment;

import com.assessment.controller.BestMatchedRestaurantsGvnController;
import com.assessment.dto.RestaurantDTO;
import com.assessment.dto.RestaurantRequestDTO;
import com.assessment.persistance.RestaurantRepository;
import com.assessment.projection.RestaurantProjection;
import com.assessment.service.BestMatchedRestaurantsGvnService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@SpringJUnitConfig
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:beforeRun.sql")
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
class BestMatchedRestaurantsGvnApplicationTests {


    @Autowired
    private BestMatchedRestaurantsGvnController controller;

    @Autowired
    private Validator validator;

    @Autowired
    private MockMvc mockMvc;

    @Value("classpath:mocks/shouldReturnOkJsonMock.json")
    private Resource shouldReturnOkJsonMock;

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private BestMatchedRestaurantsGvnService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnBadRequest() {
        //given
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        RestaurantRequestDTO requestDTO2 = new RestaurantRequestDTO();
        RestaurantRequestDTO requestDTO3 = new RestaurantRequestDTO();
        RestaurantRequestDTO requestDTO4 = new RestaurantRequestDTO();
        requestDTO.setCustomerRating(6);
        requestDTO2.setDistance(0);
        requestDTO3.setPrice(0);
        requestDTO4.setRestaurantName(" ");

        DataBinder dataBinder = new DataBinder(requestDTO);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();

        //when

        var result = controller.getListOfMatchedRestaurants(requestDTO,bindingResult);
        var result2 = controller.getListOfMatchedRestaurants(requestDTO2,bindingResult);
        var result3 = controller.getListOfMatchedRestaurants(requestDTO3,bindingResult);
        var result4 = controller.getListOfMatchedRestaurants(requestDTO4,bindingResult);

        //then
        Assertions.assertEquals(400, result.getStatusCode().value());
        Assertions.assertEquals(400, result2.getStatusCode().value());
        Assertions.assertEquals(400, result3.getStatusCode().value());
        Assertions.assertEquals(400, result4.getStatusCode().value());
    }

    @Test
    void shouldReturnListOfRestaurant() throws IOException, JSONException {
        //given
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        requestDTO.setCustomerRating(2);
        requestDTO.setDistance(10);
        requestDTO.setPrice(50);
        requestDTO.setRestaurantName("Del");
        requestDTO.setCuisine("Ch");
        DataBinder dataBinder = new DataBinder(requestDTO);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();
        String expected = Files.readString(shouldReturnOkJsonMock.getFile().toPath());

        //when
        var result = controller.getListOfMatchedRestaurants(requestDTO,bindingResult);
        ObjectMapper mapper = new ObjectMapper();
        var dto = mapper.writeValueAsString(result.getBody());

        //then
        Assertions.assertEquals(200, result.getStatusCode().value());
        JSONAssert.assertEquals(expected, dto, JSONCompareMode.STRICT);

    }

    @Test
    void shouldReturnEmptyList() throws JsonProcessingException {
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        requestDTO.setCustomerRating(4);
        requestDTO.setDistance(1);
        requestDTO.setPrice(1);
        requestDTO.setRestaurantName("Del");
        requestDTO.setCuisine("Ch");
        DataBinder dataBinder = new DataBinder(requestDTO);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();

        var result = controller.getListOfMatchedRestaurants(requestDTO,bindingResult);
        ObjectMapper mapper = new ObjectMapper();
        var dto = mapper.writeValueAsString(result.getBody());

        Assertions.assertEquals("[]", dto);

    }

    @Test
    void shouldNotReturnResultFromH2() {

        Mockito.when(repository.retrieveRestaurantInformation())
                .thenReturn(Collections.emptyList());

        List<RestaurantDTO> result = service.getRestaurantInformation(new RestaurantRequestDTO());

        Assertions.assertEquals(new ArrayList<>(), result);


    }

    @Test
    void shouldReturnListOfRestaurantsWithMockMvc() throws Exception {
        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO();
        requestDTO.setCustomerRating(2);
        requestDTO.setDistance(10);
        requestDTO.setPrice(50);
        requestDTO.setRestaurantName("Del");
        requestDTO.setCuisine("Ch");

        String expected = Files.readString(shouldReturnOkJsonMock.getFile().toPath());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getListOfMatchedRestaurants")
                        .param("customerRating", "2")
                        .param("distance", "10")
                        .param("price", "50")
                        .param("restaurantName", "Del")
                        .param("cuisine", "Ch"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, content, JSONCompareMode.STRICT);
    }



}
