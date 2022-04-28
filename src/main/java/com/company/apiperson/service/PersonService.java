package com.company.apiperson.service;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.model.entity.Person;
import com.company.apiperson.model.request.PersonRQ;
import com.company.apiperson.model.response.PersonRS;
import com.company.apiperson.repository.PersonRepository;
import com.company.apiperson.security.model.entity.User;
import com.company.apiperson.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    public boolean isUserEqualsPerson(Long idPerson, String userName) {
        return getPersonById(idPerson).getDni().equals(userName);
    }

    @Transactional
    public PersonRS savePerson(PersonRQ personRQ) {
        boolean existsUser = false;
        if(personRQ.getId()!=null){
            notExistsPerson(personRQ.getId());
            existsUser = true;
        }
        Person person = personRepository.save(mapper.personRQToPerson(personRQ));

        if(!existsUser){
            User user = mapper.buildUser(person.getDni(), person.getDni());
            Set<String> roles = new HashSet<>();
            roles.add("CLIENT");
            user.setPerson(person);
            person.setUser(userService.save(user, roles));
        }

        return mapper.personToPersonRS(person);
    }

    public Person savePersonVehiculos(Person person) {
        return personRepository.save(person);
    }

    public List<PersonRS> getAllPerson() {
        return personRepository.findAll().stream()
                .map(person -> mapper.personToPersonRS(person))
                .collect(Collectors.toList());
    }

    public Person getPersonByDni(String dni) {
        notExistsPerson(dni);
        return personRepository.findPersonByDni(dni);
    }

    public PersonRS getPersonById(Long id) {
        notExistsPerson(id);
        return mapper.personToPersonRS(personRepository.findById(id).get());
    }

    public void deletePersonById(Long id) {
        notExistsPerson(id);
        personRepository.deleteById(id);
    }

    private void notExistsPerson(Long id) {
        if (!personRepository.existsPersonById(id)) {
            throw new ServiceException(HttpStatus.NOT_FOUND,String.format("Person with id: '%s' not found", id));
        }
    }

    private void notExistsPerson(String dni) {
        if (!personRepository.existsPersonByDni(dni)) {
            throw new ServiceException(HttpStatus.NOT_FOUND,String.format("Person with dni: '%s' not found", dni));
        }
    }
}