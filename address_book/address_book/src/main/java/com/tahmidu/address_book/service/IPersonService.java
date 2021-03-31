package com.tahmidu.address_book.service;

import com.tahmidu.address_book.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {

    List<Person> findALl();
    List<Person> findAllWhereOrganisationIsNull();
    void save(Person person);
    void removeById(Long id);
    Optional<Person> findById(Long id);
}
