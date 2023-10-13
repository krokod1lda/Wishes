package com.krokod1lda.wishes.controllers;

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
        model.addAttribute("title", "Добавление продавца");
        model.addAttribute("role", "продавца");
        model.addAttribute("type", "seller");

        return "add-person";
    }

    @GetMapping("/add-buyer")
    public String addBuyer(Model model) {
        model.addAttribute("title", "Добавление байера");
        model.addAttribute("role", "байера");
        model.addAttribute("type", "buyer");

        return "add-person";
    }

    @GetMapping("/add-client")
    public String addClient(Model model) {
        model.addAttribute("title", "Добавление клиента");
        model.addAttribute("role", "клиента");
        model.addAttribute("type", "client");

        return "add-person";
    }

    @PostMapping("/addPerson")
    public String addPerson(@RequestParam("type") String type, @RequestParam("name") String name,
                            @RequestParam("patronymic") String patronymic, @RequestParam("surname") String surname) {

        personService.addPerson(type, name, patronymic, surname);

        return "redirect:/";
    }

    @GetMapping("/all-persons")
    public String allPersons(Model model) {
        model.addAttribute("map", personService.getPeopleMapForAllPersonsPage());
        model.addAttribute("title", "Все участники");

        return "all-persons";
    }

    @GetMapping("/person{id}")
    public String updatePerson(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("person", personService.updatePerson(id));
        model.addAttribute("title", "Редактирование участника");
        model.addAttribute("id", id);

        return "edit-person";
    }

    @PostMapping("/person{id}")
    public String updatePerson(@PathVariable("id") long id, @RequestParam("name") String name,
                               @RequestParam("patronymic") String patronymic, @RequestParam("surname") String surname) {

        personService.updatePerson(id, name, patronymic, surname);

        return "redirect:/all-persons";
    }

    @PostMapping("/deletePerson")
    public String deletePerson(@RequestParam("id") long id) {
        personService.deletePerson(id);

        return "redirect:/all-persons";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("title", "Статистика");

        return "statistics";
    }
}