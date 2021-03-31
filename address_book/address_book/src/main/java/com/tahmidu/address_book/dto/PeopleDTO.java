package com.tahmidu.address_book.dto;

import com.tahmidu.address_book.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class PeopleDTO {

    private List<Person> people;

    public PeopleDTO(){}

    public PeopleDTO(List<Person> people) {
        this.people = people;
    }
}
