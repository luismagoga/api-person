package com.company.apiperson.repository;

import com.company.apiperson.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsPersonById(Long id);
    boolean existsPersonByDni(String dni);
    Person findPersonByDni(String dni);
}
