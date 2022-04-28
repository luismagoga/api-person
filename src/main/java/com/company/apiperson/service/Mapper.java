package com.company.apiperson.service;

import com.company.apiperson.model.entity.Person;
import com.company.apiperson.model.entity.VehiculoPerson;
import com.company.apiperson.model.entity.VehiculoStock;
import com.company.apiperson.model.response.VehiculoRS;
import com.company.apiperson.model.response.VehiculoStockRS;
import com.company.apiperson.security.model.entity.Role;
import com.company.apiperson.security.model.entity.User;
import com.company.apiperson.security.model.request.AuthorizationRequest;
import com.company.apiperson.model.request.PersonRQ;
import com.company.apiperson.model.response.PersonRS;
import com.company.apiperson.security.model.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Mapper {


    public VehiculoStockRS vehiculoStockToVehiculoStockRS(VehiculoStock vehiculoStock) {
        VehiculoStockRS vehiculoStockRS = new VehiculoStockRS();

        vehiculoStockRS.setId(vehiculoStock.getId());
        vehiculoStockRS.setMarca(vehiculoStock.getMarca());
        vehiculoStockRS.setModelo(vehiculoStock.getModelo());
        vehiculoStockRS.setUnidades(vehiculoStock.getUnidades());

        return vehiculoStockRS;
    }

    public VehiculoPerson vehiculoStockToVehiculoPerson(VehiculoStock vehiculoStock) {
        VehiculoPerson vehiculoPerson = new VehiculoPerson();

        vehiculoPerson.setMarca(vehiculoStock.getMarca());
        vehiculoPerson.setModelo(vehiculoStock.getModelo());

        return vehiculoPerson;
    }

    public PersonRS personToPersonRS(Person person) {
        PersonRS personRS = new PersonRS();

        personRS.setId(person.getId());
        personRS.setDni(person.getDni());
        personRS.setNombre(person.getNombre());
        personRS.setFechaNacimiento(person.getFechaNacimiento());

        List<VehiculoRS> vehiculosRS = new ArrayList<>();
        for(VehiculoPerson vehiculoPerson : person.getVehiculosPerson()) {
            VehiculoRS vehiculoRS = new VehiculoRS();
            vehiculoRS.setMarca(vehiculoPerson.getMarca());
            vehiculoRS.setModelo(vehiculoPerson.getModelo());
            vehiculosRS.add(vehiculoRS);
        }
        personRS.setVehiculosRS(vehiculosRS);

        return personRS;
    }

    public Person personRQToPerson(PersonRQ personRQ) {
        Person person = new Person();

        person.setId(personRQ.getId());
        person.setDni(personRQ.getDni());
        person.setNombre(personRQ.getNombre());
        person.setFechaNacimiento(personRQ.getFechaNacimiento());
        person.setVehiculosPerson(new ArrayList<>());

        return person;
    }

    public UserDetails buildUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user));
    }

    private Set<? extends GrantedAuthority> getAuthorities(User retrievedUser) {
        Set<Role> roles = retrievedUser.getRoles();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        return authorities;
    }

    public UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setId(user.getId());
        userResponse.setIdPerson(user.getPerson()!=null ? user.getPerson().getId() : null);
        userResponse.setRoles(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()));
        return userResponse;
    }

    public User toDomain(AuthorizationRequest authorizationRequest) {
        User user = new User();
        user.setName(authorizationRequest.getUserName());
        user.setPassword(authorizationRequest.getPassword());
        return user;
    }

    public User buildUser(String userName, String password) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        return user;
    }
}
