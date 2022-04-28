package com.company.apiperson.controller;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.model.request.PersonRQ;
import com.company.apiperson.model.response.PersonRS;
import com.company.apiperson.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private Long ID1 = 1L;
    private Long ID2 = 2L;
    private Long ID_NOT_EXIST = 999L;

    private PersonRQ person() {
        PersonRQ person = new PersonRQ();
        person.setNombre("nombre");
        person.setFechaNacimiento(new Date(1651056225177L));
        person.setDni("12345678A");
        return person;
    }

    private PersonRS person100() {
        PersonRS person = new PersonRS();
        person.setId(100L);
        person.setNombre("nombre");
        person.setFechaNacimiento(new Date(1651056225177L));
        person.setDni("12345678A");
        person.setVehiculosRS(new ArrayList<>());
        return person;
    }

    private PersonRQ personWithIdNotExists() {
        PersonRQ person = new PersonRQ();
        person.setId(ID_NOT_EXIST);
        person.setNombre("Otra persona");
        person.setFechaNacimiento(new Date());
        person.setDni("98765432P");
        return person;
    }

    private String json(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private String tokenAdmin() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsIkNMQUlNX1RPS0VOIjoiUk9MRV9BRE1JTixST0xFX0NMSUVOVCxST0xFX0NPTUVSQ0lBTCIsImlhdCI6MTY1MDk3ODE2MSwiaXNzIjoiSVNTVUVSIiwiZXhwIjoxNjUwOTc4MTYxODkyfQ.Tgzx8-tE_PH8QWiaVbMfO18qd2HP4eDPl_dUvCQgBLg";
    }

    private String tokenClient() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0OTA2MzI4NlAiLCJDTEFJTV9UT0tFTiI6IlJPTEVfQ0xJRU5UIiwiaWF0IjoxNjUwOTc4MjIyLCJpc3MiOiJJU1NVRVIiLCJleHAiOjE2NTA5NzgyMjIzOTd9.48B3pm-L2spmst6z41zDtsykzYOLkrhChYCWkKlsMfU";
    }

    private String tokenUnknown() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bmtub3duIiwiQ0xBSU1fVE9LRU4iOiJST0xFX0NPTUVSQ0lBTCIsImlhdCI6MTY1MTA1NDUxMywiaXNzIjoiSVNTVUVSIiwiZXhwIjoxNjUwOTc4MTYxODl9.xmm3S4L_e46uTLr5io0nypz-f18RpWpBEvH2lTfcygE";
    }

    private String allPersonsJson() {
        return "[{\"id\":1,\"dni\":\"49063286P\",\"nombre\":\"Luis Manuel\",\"fechaNacimiento\":649720800000,\"vehiculosRS\":[{\"marca\":\"Opel\",\"modelo\":\"Astra\"}]},{\"id\":2,\"dni\":\"68236094P\",\"nombre\":\"Manuel Luis\",\"fechaNacimiento\":649807200000,\"vehiculosRS\":[]}]";
    }

    private String savePersonJson() {
        return "{\"id\":100,\"dni\":\"12345678A\",\"nombre\":\"nombre\",\"fechaNacimiento\":1651056225177,\"vehiculosRS\":[]}";
    }

    private String getPersonByIdJson() {
        return "{\"id\":1,\"dni\":\"49063286P\",\"nombre\":\"Luis Manuel\",\"fechaNacimiento\":649720800000,\"vehiculosRS\":[{\"marca\":\"Opel\",\"modelo\":\"Astra\"}]}";
    }

    @Test
    @Order(1)
    void getPersonByIdNotFound() {
        assertThrows(ServiceException.class, () -> personController.getPersonById(ID_NOT_EXIST, tokenAdmin(), null));
    }

    @Test
    @Order(2)
    void deletePersonNotFound() {
        assertThrows(ServiceException.class, () -> personController.deletePerson(ID_NOT_EXIST));
    }
    @Test
    @Order(3)
    void savePersonNotFound() {
        assertThrows(ServiceException.class, () -> personController.savePerson(personWithIdNotExists()));
    }

    @Test
    @Order(4)
    void getAllPersons() throws JsonProcessingException {
        Assertions.assertEquals(2, personController.getAllPersons().getBody().size());
    }

    @Test
    @Order(5)
    void savePerson() throws JsonProcessingException {
        Assertions.assertEquals(savePersonJson(), json(personController.savePerson(person()).getBody()));
    }

    @Test
    @Order(6)
    void getPersonById() throws JsonProcessingException {
        Assertions.assertEquals(json(person100()), json(personController.getPersonById(100L, tokenAdmin(), null).getBody()));
    }

    @Test
    @Order(7)
    void deletePerson() {
        Assertions.assertEquals(200, personController.getPersonById(ID1, tokenAdmin(), null).getStatusCodeValue());
        personController.deletePerson(ID1);
        assertThrows(ServiceException.class, () -> personController.getPersonById(ID1, tokenAdmin(),null));
    }

    @Test
    @Order(8)
    void getPersonByIdNotAuthorized() {
        assertThrows(ServiceException.class, () -> personController.getPersonById(ID2, tokenClient(),null));
    }
}