package com.tahmidu.address_book.controller;

import com.tahmidu.address_book.model.Person;
import com.tahmidu.address_book.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class PersonController {

    private final IPersonService personService;

    @Autowired
    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public String personPage(Model model){

        model.addAttribute("people", personService.findALl());
        return "person";
    }

    @GetMapping("/person/create")
    public String createPerson(Model model){

        model.addAttribute("person", new Person());
        return "create_person";
    }

    @PostMapping("/person/create/process")
    public String processPersonCreation(@ModelAttribute Person person, Model model){

        personService.save(person);
        return "redirect:/person";
    }

    @GetMapping("/person/delete/{id}")
    public String processPersonDeletion(@PathVariable(value = "id") Long id, Model model){

        personService.removeById(id);
        return "redirect:/person";
    }

    @GetMapping("/person/modify/{id}")
    public String modifyPerson(Model model, @PathVariable(value = "id") Long id){

        Optional<Person> personOptional = personService.findById(id);

        if(personOptional.isPresent()){
            Person person = personOptional.get();
            model.addAttribute("personToModify", person);
        }

        model.addAttribute("person", new Person());

        return "edit_person";
    }

    @PostMapping("/person/modify/process")
    public String modifyPerson(@ModelAttribute Person person, Model model){
        Optional<Person> toBeModifiedPersonOptional = personService.findById(person.getPersonId());

        if(toBeModifiedPersonOptional.isPresent()){
            Person toBeModifiedPerson = toBeModifiedPersonOptional.get();

            if(person.getFirstName().equals(""))
                person.setFirstName(toBeModifiedPerson.getFirstName());

            if(person.getLastName().equals(""))
                person.setLastName(toBeModifiedPerson.getLastName());

            if(person.getEmail().equals(""))
                person.setEmail(toBeModifiedPerson.getEmail());

            if(person.getPhoneNum().equals(""))
                person.setPhoneNum(toBeModifiedPerson.getPhoneNum());
        }

        personService.save(person);
        return "redirect:/person";
    }
}
