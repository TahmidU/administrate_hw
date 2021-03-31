package com.tahmidu.address_book.service;

import com.tahmidu.address_book.model.Organisation;
import com.tahmidu.address_book.model.Person;

import java.util.List;
import java.util.Optional;

public interface IOrganisationService {

    List<Organisation> findALl();
    void save(Organisation organisation);
    void removeById(Long id);
    Optional<Organisation> findById(Long id);

}
