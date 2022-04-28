package com.company.apiperson.controller;

import com.company.apiperson.model.request.PersonRQ;
import com.company.apiperson.model.response.PersonRS;
import com.company.apiperson.security.utils.Constants;
import com.company.apiperson.service.PersonService;
import com.company.apiperson.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ValidateService validateService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMERCIAL')")
    public ResponseEntity<PersonRS> savePerson(@Valid @RequestBody PersonRQ personRQ) {
        return ResponseEntity.ok(personService.savePerson(personRQ));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMERCIAL')")
    public ResponseEntity<List<PersonRS>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPerson());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COMERCIAL', 'ROLE_CLIENT')")
    public ResponseEntity<PersonRS> getPersonById(@PathVariable("id") Long id,
                                                  @RequestParam(required = false) String token, HttpServletRequest httpServletRequest) {
        if(StringUtils.isEmpty(token)) {
           token = httpServletRequest.getHeader(Constants.HEADER_AUTHORIZATION_KEY).replace(Constants.TOKEN_BEARER_PREFIX + " ", "");
        }
        validateService.validateRequestGetPerson(id, token);
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMERCIAL')")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
        personService.deletePersonById(id);
        return ResponseEntity.ok().build();
    }

}