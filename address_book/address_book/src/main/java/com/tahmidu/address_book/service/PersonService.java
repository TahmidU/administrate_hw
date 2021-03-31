package com.tahmidu.address_book.service;

import com.tahmidu.address_book.model.Person;
import com.tahmidu.address_book.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService{

    private final IPersonRepository personRepository;

    @Autowired
    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findALl(){
        return personRepository.findAll();
    }

    @Override
    public List<Person> findAllWhereOrganisationIsNull(){
        return personRepository.getPeopleWithNullOrganisation();
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void removeById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Optional<Person> findById(Long id){
        return personRepository.findById(id);
    }
}
