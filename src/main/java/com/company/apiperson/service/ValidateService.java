package com.company.apiperson.service;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.security.utils.TokenProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ValidateService {

    @Autowired
    private PersonService personService;

    public void validateRequestGetPerson(Long id, String token){
        if(!TokenProviderService.getRoles(token).contains("ROLE_ADMIN")
                && !TokenProviderService.getRoles(token).contains("ROLE_COMERCIAL")
                    && !personService.isUserEqualsPerson(id, TokenProviderService.getUserName(token))) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, String.format("Not authorized to read person with id: '%s'", id));
        }
    }
}
