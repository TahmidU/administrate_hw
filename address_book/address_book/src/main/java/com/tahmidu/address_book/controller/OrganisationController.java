package com.tahmidu.address_book.controller;

import com.tahmidu.address_book.dto.PeopleDTO;
import com.tahmidu.address_book.model.Organisation;
import com.tahmidu.address_book.model.Person;
import com.tahmidu.address_book.service.IOrganisationService;
import com.tahmidu.address_book.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class OrganisationController {

    private final IOrganisationService organisationService;
    private final IPersonService personService;

    @Autowired
    public OrganisationController(IOrganisationService organisationService, IPersonService personService) {
        this.organisationService = organisationService;
        this.personService = personService;
    }

    @GetMapping("/organisation")
    public String organisationPage(Model model){
        model.addAttribute("organisations", organisationService.findALl());
        return "organisation";
    }

    @GetMapping("/organisation/{id}")
    public String organisationIdPage(Model model, @PathVariable(name = "id") Long id){


        return "organisation_id";
    }

    @GetMapping("/organisation/create")
    public String createOrganisation(Model model){

        model.addAttribute("organisation", new Organisation());
        return "create_organisation";
    }

    @PostMapping("/organisation/create/process")
    public String processOrganisationCreation(@ModelAttribute Organisation organisation, Model model){

        organisationService.save(organisation);
        return "redirect:/organisation";
    }

    @GetMapping("/organisation/delete/{id}")
    public String processOrganisationDeletion(@PathVariable(value = "id") Long id, Model model){

        organisationService.removeById(id);
        return "redirect:/organisation";
    }

    @GetMapping("/organisation/modify/{id}")
    public String modifyOrganisation(Model model, @PathVariable(value = "id") Long id){

        Optional<Organisation> organisationOptional = organisationService.findById(id);

        organisationOptional.ifPresent(organisation ->
                model.addAttribute("organisationToModify", organisation));

        model.addAttribute("organisation", new Organisation());

        return "edit_organisation";
    }

    @PostMapping("/organisation/modify/process")
    public String modifyOrganisation(@ModelAttribute Organisation organisation, Model model){

        Optional<Organisation> organisationOptional = organisationService.findById(organisation
                .getOrganisationId());

        organisationOptional.ifPresent(toBeModifiedOrganisation -> {
            if(organisation.getName().equals(""))
                organisation.setName(toBeModifiedOrganisation.getName());

            if(organisation.getEmail().equals(""))
                organisation.setEmail(toBeModifiedOrganisation.getEmail());

            if(organisation.getNumber().equals(""))
                organisation.setNumber(toBeModifiedOrganisation.getNumber());
        });

        organisationService.save(organisation);
        return "redirect:/organisation";
    }

    @GetMapping("/organisation/manage/{id}")
    public String modifyOrganisation(@PathVariable Long id, Model model){

        Optional<Organisation> organisationOptional = organisationService.findById(id);

        organisationOptional.ifPresent(organisation -> {
            model.addAttribute("organisation", organisation);
            model.addAttribute("people", organisation.getPeople());
        });

        return "manage_organisation";
    }

    @GetMapping("/organisation/manage/{id}/add-people")
    public String addPeopleToOrganisation(@PathVariable Long id, Model model){

        List<Person> people = personService.findAllWhereOrganisationIsNull();
        Optional<Organisation> organisationOptional = organisationService.findById(id);

        organisationOptional.ifPresent(organisation -> model.addAttribute("organisation", organisation));

        model.addAttribute("people", people);
        model.addAttribute("peopleDTO", new PeopleDTO(new ArrayList<>()));

        return "add_people_to_organisation";
    }

    @PostMapping("organisation/manage/{id}/add-people/process")
    public String addPeopleToOrganisationProcess(@PathVariable Long id, @ModelAttribute PeopleDTO peopleDTO){

        Optional<Organisation> organisationOptional = organisationService.findById(id);

        organisationOptional.ifPresent(organisation -> {
            peopleDTO.getPeople().forEach(person -> {
                person.setOrganisation(organisation);
                personService.save(person);
            });
            organisation.setPeople(peopleDTO.getPeople());
            organisationService.save(organisation);
        });

        return "redirect:/organisation/manage/"+id;
    }

    @GetMapping("/organisation/manage/{id}/remove-people")
    public String removePeopleFromOrganisation(@PathVariable Long id, Model model){

        Optional<Organisation> organisationOptional = organisationService.findById(id);

        organisationOptional.ifPresent(organisation -> {
            model.addAttribute("organisation", organisation);
            List<Person> people = organisation.getPeople();

            if(people != null) {

                List<Person> peopleWithOrganisationRemoved = new ArrayList<>();

                people.forEach(person -> {
                    person.setOrganisation(null);
                    peopleWithOrganisationRemoved.add(person);
                });

                model.addAttribute("people", peopleWithOrganisationRemoved);
            }
            else
                model.addAttribute("people", new ArrayList<>());
        });

        model.addAttribute("peopleDTO", new PeopleDTO(new ArrayList<>()));

        return "remove_people_from_organisation";
    }

    @PostMapping("/organisation/manage/{id}/remove-people/process")
    public String removePeopleFromOrganisationProcess(@PathVariable Long id, @ModelAttribute PeopleDTO peopleDTO){

        Optional<Organisation> organisationOptional = organisationService.findById(id);

        organisationOptional.ifPresent(organisation -> {
            peopleDTO.getPeople().forEach(person -> {
                person.setOrganisation(null);
                personService.save(person);
            });

            List<Person> newPeople = new ArrayList<>();
            List<Person> removePeople = peopleDTO.getPeople();
            List<Person> organisationPeople = organisation.getPeople();

            for(Person person : organisationPeople){
                boolean isFound = false;
                for(Person personRemove : removePeople){
                    if(person.getPersonId().equals(personRemove.getPersonId())){
                        isFound = true;
                        break;
                    }
                }
                if(!isFound)
                    newPeople.add(person);
            }

            if(!newPeople.isEmpty())
                organisation.setPeople(newPeople);
            else
                organisation.setPeople(null);

        });

        return "redirect:/organisation/manage/"+id;
    }
}
