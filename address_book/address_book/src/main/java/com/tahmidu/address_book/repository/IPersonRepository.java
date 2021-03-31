package com.tahmidu.address_book.repository;

import com.tahmidu.address_book.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p LEFT JOIN p.organisation o WHERE o IS NULL")
    List<Person> getPeopleWithNullOrganisation();

}
