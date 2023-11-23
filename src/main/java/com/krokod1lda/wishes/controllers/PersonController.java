package com.krokod1lda.wishes.controllers;

import com.krokod1lda.wishes.EntityAttributes.PersonAttributes;
import com.krokod1lda.wishes.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/add-seller")
    public String addSeller(Model model) {
        model.addAttribute(PersonAttributes.TITLE.getValue(), PersonAttributes.TITLE_ADDING_SELLER.getValue());
        model.addAttribute(PersonAttributes.ROLE.getValue(),  PersonAttributes.ROLE_SELLER.getValue());
        model.addAttribute(PersonAttributes.TYPE.getValue(), PersonAttributes.TYPE_SELLER.getValue());

        return "add-person";
    }

    @GetMapping("/add-buyer")
    public String addBuyer(Model model) {
        model.addAttribute(PersonAttributes.TITLE.getValue(), PersonAttributes.TITLE_ADDING_BUYER.getValue());
        model.addAttribute(PersonAttributes.ROLE.getValue(),  PersonAttributes.ROLE_BUYER.getValue());
        model.addAttribute(PersonAttributes.TYPE.getValue(), PersonAttributes.TYPE_BUYER.getValue());

        return "add-person";
    }

    @GetMapping("/add-client")
    public String addClient(Model model) {
        model.addAttribute(PersonAttributes.TITLE.getValue(), PersonAttributes.TITLE_ADDING_CLIENT.getValue());
        model.addAttribute(PersonAttributes.ROLE.getValue(),  PersonAttributes.ROLE_CLIENT.getValue());
        model.addAttribute(PersonAttributes.TYPE.getValue(), PersonAttributes.TYPE_CLIENT.getValue());

        return "add-person";
    }

    @PostMapping("/addPerson")
    public String addPerson(@RequestParam("type") String type, @RequestParam("name") String name,
                           @RequestParam("surname") String surname) {

        personService.addPerson(type, name, surname);

        return "redirect:/";
    }

    @GetMapping("/all-persons")
    public String allPersons(Model model) {
        model.addAttribute(PersonAttributes.MAP.getValue(), personService.getPeopleMapForAllPersonsPage());
        model.addAttribute(PersonAttributes.TITLE.getValue(), PersonAttributes.TITLE_ALL_PARTICIPANTS.getValue());

        return "all-persons";
    }

    @GetMapping("/person/{id}")
    public String updatePerson(@PathVariable("id") long id, Model model) {
        model.addAttribute(PersonAttributes.PERSON.getValue(), personService.updatePerson(id));
        model.addAttribute(PersonAttributes.TITLE.getValue(), PersonAttributes.EDITING_PARTICIPANT.getValue());
        model.addAttribute(PersonAttributes.ID.getValue(), id);

        return "edit-person";
    }

    @PostMapping("/person/{id}")
    public String updatePerson(@PathVariable("id") long id, @RequestParam("name") String name,
                               @RequestParam("surname") String surname) {

        personService.updatePerson(id, name, surname);

        return "redirect:/all-persons";
    }

    @GetMapping("/archivedPerson/{id}")
    public String archivedPerson(@PathVariable("id") long id, Model model) {
        model.addAttribute(PersonAttributes.PERSON.getValue(), personService.updatePerson(id));
        model.addAttribute(PersonAttributes.TITLE.getValue(), PersonAttributes.EDITING_ARCHIVED_PARTICIPANT.getValue());
        model.addAttribute(PersonAttributes.ID.getValue(), id);

        return "edit-archived-person";
    }

    @PostMapping("/archivePerson")
    public String archivePerson(@RequestParam("id") long id) {
        personService.archivePerson(id);

        return "redirect:/all-persons";
    }


    @PostMapping("unarchivePerson")
    public String unarchivePerson(@RequestParam("id") long id) {
        personService.unarchivePerson(id);

        return "redirect:/all-persons";
    }

    @PostMapping("/deletePerson")
    public String deletePerson(@RequestParam("id") long id) {
        personService.deletePerson(id);

        return "redirect:/all-persons";
    }
}