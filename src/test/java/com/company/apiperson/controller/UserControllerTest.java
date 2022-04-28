package com.company.apiperson.controller;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.security.model.entity.User;
import com.company.apiperson.security.model.request.AuthorizationRequest;
import com.company.apiperson.security.model.response.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController userController;

    private Long ID_NOT_EXIST = 999L;

    @Test
    @Order(1)
    void getUserOK() throws JsonProcessingException {
        ResponseEntity<UserResponse> response = userController.getUser(1L);
        if(userResponse().equals(json(response.getBody()))) {
            Assertions.assertEquals(userResponse(),  json(response.getBody()));
        } else if(userResponse1().equals(json(response.getBody()))) {
            Assertions.assertEquals(userResponse1(),  json(response.getBody()));
        } else if(userResponse2().equals(json(response.getBody()))) {
            Assertions.assertEquals(userResponse2(),  json(response.getBody()));
        } else if(userResponse3().equals(json(response.getBody()))) {
            Assertions.assertEquals(userResponse3(),  json(response.getBody()));
        }else if(userResponse4().equals(json(response.getBody()))) {
            Assertions.assertEquals(userResponse4(),  json(response.getBody()));
        } else {
            Assertions.assertEquals(userResponse5(),  json(response.getBody()));
        }

    }

    @Test
    @Order(2)
    void getUserNotFound() {
        assertThrows(ServiceException.class, () -> userController.getUser(ID_NOT_EXIST));
    }

    @Test
    @Order(3)
    void getAllUsers() {
        Assertions.assertEquals(200,  userController.getAllUsers().getStatusCodeValue());
    }

    @Test
    @Order(4)
    void saveUserOK() {
        Assertions.assertEquals(200,  userController.saveUser(authorizationRequest()).getStatusCodeValue());
    }

    @Test
    @Order(5)
    void deleteUserOK() {
        Assertions.assertEquals(200,  userController.deleteUser(100L).getStatusCodeValue());
    }

    private String json(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private String userResponse() {
        return "{\"id\":1,\"name\":\"admin\",\"roles\":[\"CLIENT\",\"ADMIN\",\"COMERCIAL\"],\"idPerson\":null}";
    }
    private String userResponse1() {
        return "{\"id\":1,\"name\":\"admin\",\"roles\":[\"ADMIN\",\"CLIENT\",\"COMERCIAL\"],\"idPerson\":null}";
    }
    private String userResponse2() {
        return "{\"id\":1,\"name\":\"admin\",\"roles\":[\"COMERCIAL\",\"ADMIN\",\"CLIENT\"],\"idPerson\":null}";
    }
    private String userResponse3() {
        return "{\"id\":1,\"name\":\"admin\",\"roles\":[\"CLIENT\",\"COMERCIAL\",\"ADMIN\"],\"idPerson\":null}";
    }
    private String userResponse4() {
        return "{\"id\":1,\"name\":\"admin\",\"roles\":[\"ADMIN\",\"COMERCIAL\",\"CLIENT\"],\"idPerson\":null}";
    }
    private String userResponse5() {
        return "{\"id\":1,\"name\":\"admin\",\"roles\":[\"COMERCIAL\",\"CLIENT\",\"ADMIN\"],\"idPerson\":null}";
    }

    private AuthorizationRequest authorizationRequest() {
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();

        authorizationRequest.setUserName("mila");
        authorizationRequest.setPassword("milagros");
        Set<String> roles = new HashSet<>();
        roles.add("CLIENT");
        authorizationRequest.setRoles(roles);

        return authorizationRequest;
    }
}
