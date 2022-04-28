package com.company.apiperson.controller;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.model.request.MarketRQ;
import com.company.apiperson.model.response.VehiculoStockRS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarketControllerTest {

    @Autowired
    private MarketController marketController;

    @Test
    @Order(1)
    void getAllVehiculosStockOK() throws JsonProcessingException {
        ResponseEntity<List<VehiculoStockRS>> response = marketController.getAllVehiculosStock();
        Assertions.assertEquals(listaVehiculosJson(),  json(response.getBody()));
    }

    @Test
    @Order(2)
    void saveVehiculoPersonKOPersonNotFound() {
        assertThrows(ServiceException.class, () ->marketController.compraVehiculoPerson(marquetRQPersonNotFound()));
    }

    @Test
    @Order(3)
    void saveVehiculoPersonKOVehiculoNotFound() {
        assertThrows(ServiceException.class, () ->marketController.compraVehiculoPerson(marquetRQVehiculoNotFound()));
    }

    @Test
    @Order(4)
    void saveVehiculoPersonOK() {
        ResponseEntity<Void> response = marketController.compraVehiculoPerson(marquetRQOK());
        Assertions.assertEquals(200,  response.getStatusCodeValue());
    }

    private MarketRQ marquetRQOK(){
        MarketRQ marketRQ = new MarketRQ();

        marketRQ.setDniPerson("49063286P");
        marketRQ.setIdVehiculo(1L);

        return marketRQ;
    }

    private MarketRQ marquetRQPersonNotFound(){
        MarketRQ marketRQ = new MarketRQ();

        marketRQ.setDniPerson("XXX");
        marketRQ.setIdVehiculo(1L);

        return marketRQ;
    }

    private MarketRQ marquetRQVehiculoNotFound(){
        MarketRQ marketRQ = new MarketRQ();

        marketRQ.setDniPerson("49063286P");
        marketRQ.setIdVehiculo(99L);

        return marketRQ;
    }

    private String listaVehiculosJson() {
        return "[{\"id\":1,\"marca\":\"Opel\",\"modelo\":\"Astra\",\"unidades\":9},{\"id\":2,\"marca\":\"Audi\",\"modelo\":\"A4\",\"unidades\":10},{\"id\":3,\"marca\":\"Renault\",\"modelo\":\"Megane\",\"unidades\":10},{\"id\":4,\"marca\":\"Seat\",\"modelo\":\"Ibiza\",\"unidades\":10},{\"id\":5,\"marca\":\"Peugeot\",\"modelo\":\"308\",\"unidades\":10}]";
    }

    private String json(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
